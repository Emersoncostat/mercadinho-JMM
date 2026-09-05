package br.ufape.poo.mercado;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.repository.LoteRepository;
import br.ufape.poo.mercado.repository.MercadoRepository;

@SpringBootTest(classes = MercadinhoJmmApplication.class)
@ActiveProfiles("test")
class MercadoApplicationTests {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private MercadoRepository mercadoRepository;

	@Test
	void testarSalvarLoteEMercado() {

		Mercado mercado = new Mercado();
		mercado.setNome("Mercadinho JMM");
		mercado.setCnpj("12.345.678/0001-99");
		mercado.setEndereco("Rua Principal, 123");
		mercado.setTelefone("(87) 99999-0000");
		mercado.setEmail("contato@jmm.com");
		mercado.setHorarioFuncionamento("07:00 as 22:00");

		mercadoRepository.save(mercado);


		Lote lote = new Lote();
		lote.setCategoriaDoProduto("Laticínios");
		lote.setMarcaDoProduto("Piracanjuba");
		lote.setQuantidade(100);
		lote.setCodigo(1001);
		lote.setValorTotalDoLote(450.00);
		lote.setFabricacao(LocalDate.now());
		lote.setValidade(LocalDate.now().plusMonths(6));

		loteRepository.save(lote);
	}
}
