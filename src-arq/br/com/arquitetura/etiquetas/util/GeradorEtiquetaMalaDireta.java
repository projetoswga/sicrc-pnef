package br.com.arquitetura.etiquetas.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.etiquetas.anotacoes.ColunaEtiqueta;
import br.com.arquitetura.etiquetas.vo.MalaDiretoVO;
import br.com.arquitetura.util.StringUtil;
import br.com.sicrc.entidade.Representante;

public class GeradorEtiquetaMalaDireta {

    public synchronized static void gerarEtiqueta(InputStream isRelatorio, List<Representante> lista, String... parametros) throws JRException, IOException {
        if (lista != null && !lista.isEmpty()) {
            List<MalaDiretoVO> listaAux = new ArrayList<MalaDiretoVO>();
            for (Representante representante : lista) {
                listaAux.add(new MalaDiretoVO(parametros[0], montaDadosRemetente(representante)));
            }

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

            JRDataSource colecao = new JRBeanCollectionDataSource(lista == null ? new ArrayList<MalaDiretoVO>() : listaAux);
            JasperReport jp = JasperCompileManager.compileReport(isRelatorio);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, montaParametros(parametros), colecao);

            JRExporter jrExporter = new JRPdfExporter();
            String contentType = "application/pdf";

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=etiqetas_maladireta.pdf");

            ServletOutputStream outputStream = response.getOutputStream();

            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            jrExporter.exportReport();
            outputStream.flush();
            outputStream.close();
            context.renderResponse();
            context.responseComplete();
        }

    }

    private static Map<String, Object> montaParametros(String[] parametros) {
        if (parametros != null && parametros.length > 0) {
            int count = 0;
            Map<String, Object> params = new TreeMap<String, Object>();
            for (String param : parametros) {
                params.put("param" + count++, param);
            }
            return params;
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private static String montaDadosRemetente(Representante representante) {
        StringBuilder sb = new StringBuilder("<br>DESTINATÁRIO:</br>");
        for (Field campo : ordenaCampoEtiqueta(representante.getClass().getDeclaredFields())) {
            ColunaEtiqueta coluna = campo.getAnnotation(ColunaEtiqueta.class);
            if (coluna == null) {
                continue;
            }
            Entidade entidade = null;
            String nomeCampo = null;
            try {
                if (campo.getType().getSuperclass() == Entidade.class) {
                    Method metodo = representante.getClass().getMethod("get" + StringUtil.primeiraLetraMaiuscula(campo.getName()));

                    entidade = (Entidade) metodo.invoke(representante);
                    nomeCampo = coluna.atributoObjetoRelatorio();
                } else {
                    entidade = representante;
                    nomeCampo = campo.getName();
                }

                Method metodo = entidade.getClass().getMethod("get" + StringUtil.primeiraLetraMaiuscula(nomeCampo));
                Object valor = metodo.invoke(entidade);
                if (valor != null) {
                    sb.append("<br>" + valor + "</br>");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    private static List<Field> ordenaCampoEtiqueta(Field[] declaredFields) {
        if (declaredFields != null) {
            List<Field> listaCampos = new ArrayList<Field>();
            Map<Integer, Field> mapaCampos = new TreeMap<Integer, Field>();

            for (Field campo : declaredFields) {
                ColunaEtiqueta coluna = campo.getAnnotation(ColunaEtiqueta.class);
                if (coluna == null) {
                    continue;
                } else {
                    int indice = obtemIndice(coluna.ordem(), mapaCampos);
                    mapaCampos.put(indice, campo);
                }
            }

            listaCampos.addAll(mapaCampos.values());

            return listaCampos;
        }
        return null;
    }

    private static int obtemIndice(int ordem, Map<Integer, Field> mapaCampos) {
        int indice = ordem;
        if (mapaCampos != null) {
            if (mapaCampos.get(indice) != null) {
                indice = obtemIndice(indice + 1, mapaCampos);
            }
        }
        return indice;
    }

}
