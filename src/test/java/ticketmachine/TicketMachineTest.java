package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	@Test
	void dontPrintIfNotEnough(){
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(),"Pas assez d'argent");
	}
	 // S4 :  on imprime le ticket si le montant inséré est suffisant
	@Test
	void printOnlyIfAmountIsEnough(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(),"Pas assez d'argent");
	}

	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	@Test
	void WhenPrintTicketBalanceIsDecremented (){
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0,machine.getBalance());
	}

	//S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	@Test
	void amountIsUpdatedWhenTicketIsPrinted(){
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(50, machine.getTotal());
	}
	//S7 : refund()rend correctement la monnaie
	@Test
	void RefundgiveMoneyCorrectly(){
		machine.insertMoney(PRICE+10);
		assertEquals(PRICE+10, machine.refund());
	}
	// S8 :  refund()remet la balance à zéro
	@Test
	void refundMakeBalanceEqualToZero(){
		machine.insertMoney(PRICE+10);
		machine.refund();
		assertEquals(0, machine.getBalance());
	}
	// S9 :  on ne peut pas insérerun montant négatif
	@Test
	void inputCannotBeNegative(){
		assertThrows(IllegalArgumentException.class,
		 () -> {machine.insertMoney(-10);},
		"on ne peut pas inserer de value negative");
	}

	//S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	@Test
	void cannotCreateMachineWithTicketPriceNegative(){
		assertThrows(IllegalArgumentException.class,
		 () -> {TicketMachine m = new TicketMachine(-10);},
		"on ne peut pas inserer de value negative");
	}
	
}
