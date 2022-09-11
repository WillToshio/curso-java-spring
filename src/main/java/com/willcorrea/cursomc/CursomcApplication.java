package com.willcorrea.cursomc;





import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.willcorrea.cursomc.domain.Categoria;
import com.willcorrea.cursomc.domain.Cidade;
import com.willcorrea.cursomc.domain.Cliente;
import com.willcorrea.cursomc.domain.Endereco;
import com.willcorrea.cursomc.domain.Estado;
import com.willcorrea.cursomc.domain.ItemPedido;
import com.willcorrea.cursomc.domain.Pagamento;
import com.willcorrea.cursomc.domain.PagamentoComBoleto;
import com.willcorrea.cursomc.domain.PagamentoComCartao;
import com.willcorrea.cursomc.domain.Pedido;
import com.willcorrea.cursomc.domain.Produto;
import com.willcorrea.cursomc.domain.enums.EstadoPagamento;
import com.willcorrea.cursomc.domain.enums.TipoCliente;
import com.willcorrea.cursomc.repositories.CategoriaRepository;
import com.willcorrea.cursomc.repositories.CidadeRepository;
import com.willcorrea.cursomc.repositories.ClienteRepository;
import com.willcorrea.cursomc.repositories.EnderecoRepository;
import com.willcorrea.cursomc.repositories.EstadoRepository;
import com.willcorrea.cursomc.repositories.ItemPedidoRepository;
import com.willcorrea.cursomc.repositories.PagamentoRepository;
import com.willcorrea.cursomc.repositories.PedidoRepository;
import com.willcorrea.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository catRepository;
	@Autowired
	private ProdutoRepository prodRepository;
	@Autowired
	private CidadeRepository cityRepository;
	@Autowired
	private EstadoRepository estRepository;
	@Autowired
	private ClienteRepository cliRepository;
	@Autowired
	private EnderecoRepository endRepository;
	@Autowired
	private PedidoRepository pedRepository;
	@Autowired
	private PagamentoRepository pagatoRepository;
	@Autowired
	private ItemPedidoRepository ipRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	
		// Adding categories on db
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		// Adding products on db
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		// Adding States on db
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		// Adding cities on db
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		Cliente cli1 = new Cliente(null, "Maria Silva","mariasilva@gmail.com","36378912377", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.setPagamento(pagto1);
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.setPagamento(pagto2);
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		
		cat1.getProdutos().addAll(Arrays.asList( p1, p2, p3 ));
		cat2.getProdutos().addAll(Arrays.asList( p2 ));
		
		p1.getCategorias().addAll(Arrays.asList( cat1 ));
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getCategorias().addAll(Arrays.asList( cat1, cat2 ));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getCategorias().addAll(Arrays.asList( cat1 ));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		est1.getCidades().addAll(Arrays.asList( c1 ));
		est2.getCidades().addAll(Arrays.asList( c2, c3 ));
		
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		catRepository.saveAll(Arrays.asList( cat1, cat2 ));
		prodRepository.saveAll(Arrays.asList( p1, p2, p3 ));
	    estRepository.saveAll(Arrays.asList( est1, est2 ));
	    cityRepository.saveAll(Arrays.asList( c1, c2, c3 ));
	    
	    cliRepository.saveAll(Arrays.asList( cli1 ));
	    endRepository.saveAll(Arrays.asList( e1, e2 ));
	    pedRepository.saveAll(Arrays.asList( ped1, ped2 ));
	    pagatoRepository.saveAll(Arrays.asList( pagto1, pagto2 ));
	    ipRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
