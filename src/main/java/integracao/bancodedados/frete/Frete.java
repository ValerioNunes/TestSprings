package integracao.bancodedados.frete;

import integracao.bancodedados.cliente.Cliente;
import integracao.bancodedados.cidade.Cidade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Frete {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull(message="O cliente deve ser preenchido")
	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST})
	private Cliente cliente;

	@NotNull(message="A cidade deve ser preenchida")
	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST})
	private Cidade cidade;

	@NotBlank(message="A descricao deve ser preenchida")
	private String descricao;

	@PositiveOrZero(message="O peso deve ser preenchido")
	private double peso;

	@PositiveOrZero(message="O valor deve ser preenchido")
	private double valor;

	public Frete() {}

	public Frete(double valor, String descricao, double peso, Cliente cliente ,Cidade cidade) {
		this.valor = valor + valor*cidade.gettaxa()/100;
		this.descricao = descricao;
		this.peso = peso;
		this.cidade= cidade;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
