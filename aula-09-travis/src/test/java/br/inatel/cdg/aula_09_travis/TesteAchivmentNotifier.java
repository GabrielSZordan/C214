package br.inatel.cdg.aula_09_travis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TesteAchivmentNotifier {

	@Mock
	RecebedorMSG rm,rm2,rm3;
	
	AchievmentMessages achMsg;
	
	private final String MSG = "This is Dark Souls";
	
	@Before
	public void setUp() {
		achMsg = new AchievmentMessages();
	}
	
	@Test(expected = SemRecebedoresException.class)
	public void test() {
		achMsg.distribuirMensagem(MSG);
	}
	
	@Test
	public void testComUmRecebedor() {
		
		achMsg.adicionaRecebedor(rm);
		achMsg.distribuirMensagem(MSG);
		Mockito.verify(rm).recebedorMensagem(MSG);
	}
	
	@Test
	public void testComVarioesRecebedores() {
		achMsg.adicionaRecebedor(rm);
		achMsg.adicionaRecebedor(rm2);
		achMsg.distribuirMensagem(MSG);
		Mockito.verify(rm).recebedorMensagem(MSG);
		Mockito.verify(rm2).recebedorMensagem(MSG);
	}
	
	@Test
	public void removerRecebedor() {
		achMsg.adicionaRecebedor(rm);
		achMsg.adicionaRecebedor(rm2);
		achMsg.removerRecebedor(rm);
		achMsg.distribuirMensagem(MSG);
		Mockito.verify(rm, Mockito.never()).recebedorMensagem(MSG);
		Mockito.verify(rm2).recebedorMensagem(MSG);
	}
	
	@Test
	public void erroRecebedor() {
		achMsg.adicionaRecebedor(rm);
		achMsg.adicionaRecebedor(rm2);
		achMsg.adicionaRecebedor(rm3);
		
		Mockito.doThrow(RuntimeException.class)
		.when(rm).recebedorMensagem(MSG);
		
		achMsg.distribuirMensagem(MSG);
		
		Mockito.verify(rm).recebedorMensagem(MSG);
		Mockito.verify(rm2).recebedorMensagem(MSG);
		Mockito.verify(rm3).recebedorMensagem(MSG);
		
	}
}