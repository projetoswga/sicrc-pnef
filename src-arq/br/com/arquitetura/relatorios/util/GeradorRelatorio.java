package br.com.arquitetura.relatorios.util;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.ReflectiveReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.relatorios.anotacoes.ColunaRelatorio;
import br.com.arquitetura.relatorios.anotacoes.Relatorio;
import br.com.arquitetura.util.StringUtil;

public class GeradorRelatorio {

    @SuppressWarnings({ "rawtypes" })
    public synchronized static void gerarRelatorio(List<? extends Entidade> lista, String formato, String caminhoImagem) throws JRException, IOException {
        if (lista != null && !lista.isEmpty()) {
            DynamicReport relatorio = criaRelatorioDinamico(lista, caminhoImagem);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

            JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(relatorio, new ClassicLayoutManager(), lista);

            JRExporter jrExporter = null;
            String contentType = null;

            if ("PDF".equalsIgnoreCase(formato)) {
                jrExporter = new JRPdfExporter();
                contentType = "application/pdf";
            }

            if ("XLS".equalsIgnoreCase(formato)) {
                jrExporter = new JRXlsExporter();
                contentType = "application/vnd.ms-excel";
                jrExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            }

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=" + relatorio.getReportName() + "." + formato.toLowerCase());

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static DynamicReport criaRelatorioDinamico(List<? extends Entidade> lista, String caminhoImagem) {
        DynamicReportBuilder relatorioBuilder = new DynamicReportBuilder();

        Class<Entidade> classeEntidadeRelatorio = (Class<Entidade>) lista.get(0).getClass();
        Relatorio relatorioAnnotation = classeEntidadeRelatorio.getAnnotation(Relatorio.class);

        if (relatorioAnnotation != null) {
            relatorioBuilder.setPageSizeAndOrientation(relatorioAnnotation.orientacao().getPage());
            relatorioBuilder.addFirstPageImageBanner(caminhoImagem, new Integer(197), new Integer(60), ImageBanner.ALIGN_LEFT);
            relatorioBuilder.setReportName(obtemNomeArquivo(relatorioAnnotation));
            relatorioBuilder.setTitle(obtemTitulo(relatorioAnnotation));
            relatorioBuilder.setOddRowBackgroundStyle(obtemEstiloLinhaPar());
            relatorioBuilder.setPrintBackgroundOnOddRows(true);
            relatorioBuilder.setUseFullPageWidth(true);
            relatorioBuilder.setDefaultStyles(getEstiloTitulo(), geraEstiloSubTitulo(), geraEstiloCabecalho(), geraEstiloDetalhe());
            relatorioBuilder.addStyle(adicionaEstiloSubTitulo());
            relatorioBuilder = geraColunasRelatorio(relatorioBuilder, classeEntidadeRelatorio);
        } else {
            relatorioBuilder = new ReflectiveReportBuilder(lista);
        }

        return relatorioBuilder.build();
    }

    private static Style geraEstiloDetalhe() {
        return null;
    }

    @SuppressWarnings("deprecation")
    private static Style geraEstiloCabecalho() {
        Style estilo = new Style();
        estilo.setBackgroundColor(new Color(230, 230, 230));
        estilo.setBorderBottom(Border.THIN());
        estilo.setBorderColor(Color.black);
        estilo.setHorizontalAlign(HorizontalAlign.CENTER);
        estilo.setTransparency(Transparency.OPAQUE);
        return estilo;
    }

    private static Style adicionaEstiloSubTitulo() {
        Style estilo = new Style("subtitleParent");
        estilo.setBackgroundColor(Color.CYAN);
        estilo.setTransparency(Transparency.OPAQUE);
        return estilo;
    }

    private static Style geraEstiloSubTitulo() {
        Style estilo = Style.createBlankStyle("subtitleStyle", "subtitleParent");
        estilo.setFont(Font.GEORGIA_SMALL_BOLD);
        return estilo;
    }

    private static Style getEstiloTitulo() {
        Style estilo = new Style("titleStyle");
        estilo.setBackgroundColor(Color.CYAN);
        estilo.setHorizontalAlign(HorizontalAlign.CENTER);
        estilo.setFont(Font.ARIAL_BIG);
        return estilo;
    }

    @SuppressWarnings("rawtypes")
    private static DynamicReportBuilder geraColunasRelatorio(DynamicReportBuilder relatorioBuilder, Class<Entidade> classeEntidadeRelatorio) {
        for (Field campo : classeEntidadeRelatorio.getDeclaredFields()) {
            ColunaRelatorio colunaRelatorio = campo.getAnnotation(ColunaRelatorio.class);
            if (colunaRelatorio == null) {
                continue;
            }
            relatorioBuilder.addColumn(geraColuna(campo, colunaRelatorio));
        }
        return relatorioBuilder;
    }

    private static AbstractColumn geraColuna(Field campo, ColunaRelatorio colunaRelatorio) {
        ColumnBuilder colunaBuilder = ColumnBuilder.getNew();
        colunaBuilder.setColumnProperty(campo.getName(), campo.getType());
        colunaBuilder.setTitle(colunaRelatorio.titulo());
        colunaBuilder.setWidth(colunaRelatorio.largura());
        colunaBuilder.setStyle(getEstiloColuna(colunaRelatorio));
        if (campo.getType() == Boolean.class) {
            colunaBuilder.setCustomExpression(getExpressaoCampoBoleano(campo.getName()));
        }
        if (campo.getType() == Date.class) {
            colunaBuilder.setPattern("dd/MM/yyyy");
        }
        if (campo.getType().getSuperclass() == Entidade.class) {
            colunaBuilder.setCustomExpression(getExpressaoCampoEntidade(campo, colunaRelatorio));
        }
        return colunaBuilder.build();
    }

    private static CustomExpression getExpressaoCampoEntidade(final Field campo, final ColunaRelatorio colunaRelatorio) {
        CustomExpression expressao = new CustomExpression() {

            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("rawtypes")
            public Object evaluate(Map fields, Map variables, Map parameters) {
                Entidade entidade = (Entidade) fields.get(campo.getName());
                try {
                    if (entidade != null) {
                        Method metodo = entidade.getClass().getMethod("get" + StringUtil.primeiraLetraMaiuscula(colunaRelatorio.atributoObjetoRelatorio()));
                        return metodo.invoke(entidade);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };

        return expressao;
    }

    private static Style getEstiloColuna(ColunaRelatorio colunaRelatorio) {
        Style estilo = new Style();
        estilo.setHorizontalAlign(colunaRelatorio.alinhamento().getAlinhamento());
        return estilo;
    }

    private static CustomExpression getExpressaoCampoBoleano(final String campo) {
        CustomExpression expressao = new CustomExpression() {

            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("rawtypes")
            public Object evaluate(Map fields, Map variables, Map parameters) {
                Boolean valor = (Boolean) fields.get(campo);
                return valor ? "Sim" : "Não";
            }

            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };

        return expressao;
    }

    private static Style obtemEstiloLinhaPar() {
        Style estilo = new Style();
        estilo.setBackgroundColor(new Color(230, 230, 230));
        return estilo;
    }

    private static String obtemTitulo(Relatorio relatorioAnnotation) {
        return relatorioAnnotation.titulo();
    }

    protected static String obtemNomeArquivo(Relatorio relatorioAnnotation) {
        return relatorioAnnotation.nomeArquivo();
    }

}