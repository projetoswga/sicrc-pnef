package br.com.sicrc.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import br.com.arquitetura.entidade.Entidade;
import br.com.arquitetura.relatorios.anotacoes.ALINHAMENTO_HORIZONTAL;
import br.com.arquitetura.relatorios.anotacoes.ColunaRelatorio;
import br.com.arquitetura.relatorios.anotacoes.Relatorio;

@Relatorio(titulo = "Cargos Autoridades", nomeArquivo = "cargosautoridades")
@Entity
@Table(name = "cargo_autoridade", schema = "SICRC")
@Audited
public class CargoAutoridade extends Entidade<Integer> {

    private static final long serialVersionUID = 1071933224109206991L;

    @Id
    @SequenceGenerator(name = "cargo_autoridade_id_cargo_autoridade_generator", sequenceName = "sicrc.cargo_autoridade_id_cargo_autoridade_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_autoridade_id_cargo_autoridade_generator")
    @Column(name = "id_cargo_autoridade", unique = true, nullable = false)
    private Integer id;

    @Transient
    @ColunaRelatorio(titulo = "Gênero", largura = 20, alinhamento = ALINHAMENTO_HORIZONTAL.CENTRO)
    private String generoFormatado;

    @ColunaRelatorio(titulo = "Descrição", largura = 50)
    @Column(name = "dsc_cargo_autoridade")
    private String descricao;

    @Column(name = "genero_cargo_autoridade")
    private String genero;

    @ColunaRelatorio(titulo = "Complemento", largura = 50)
    @Column(name = "cmp_cargo_autoridade")
    private String complemento;

    public CargoAutoridade() {
    }

    public CargoAutoridade(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getGeneroFormatado() {
        if (genero != null) {
            return genero.toUpperCase().equals("M") ? "Masculino" : "Feminino";
        }
        return generoFormatado;
    }

    public void setGeneroFormatado(String generoFormatado) {
        this.generoFormatado = generoFormatado;
    }
}