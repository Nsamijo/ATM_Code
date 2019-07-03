package Systemfiles;

import java.awt.Color;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Date;

/**
 * @author nsami
 *
 */
public class ATM {
	// alle fields worden hier gedefined
	ATMScreen as;
	private Bank bank;
	boolean check = false;
	boolean wrong = false;
	boolean sessionClient = false;

	DisplayText welcome;
	DisplayText welcome2;
	Dimension screenSize;

	Frame f;

	Point position;
	Point position2;

	String action = null;
	String keys = "";
	String message;
	String message2;
	String pinMessage;
	String error;
	String error2;
	String tempPin = "";
	String blocked;
	String choice = null;
	String amount = "";
	int middle;
	int tempPinLength = 0;
	int attempts = 0;

	ScreenButton bWithdraw;
	ScreenButton bCheck;
	ScreenButton bfast;
	ScreenButton bexit;
	ScreenButton bBack;

	ScreenButton take20;
	ScreenButton take50;
	ScreenButton take100;
	ScreenButton take200;
	ScreenButton specifyAmount;

	ScreenButton yes;
	ScreenButton no;
	Serial serial;

	public ATM(){
		super();
		bank = new Bank();
		as = new ATMScreen();
		this.textWelcome();
		this.createFrame();
		this.setDisplayText();
		serial = new Serial();
		while(true) {
			try {
				this.doTransaction();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void createFrame() {
		f = new Frame("My ATM");
		f.setBackground(Color.BLACK);
		f.addWindowListener(new MyWindowAdapter(f));
		f.add(as);
		// f.setBounds(400, 400, 800, 800);
		f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setResizable(false);
		f.setVisible(true);

	}

	public void createReceipt(String taken) {
		as.clear();
		as.add(welcome);
		as.add(welcome2);
	}

	// hierbij worden de labels toegevoegd aan de container zodat er text kan worden
	// getoond in de frame
	public void welcomeScreen() {
		as.add(welcome);
		as.add(welcome2);
	}
	
	void options(String amount) {
		int money = Integer.valueOf(amount);
		int left = money % 50;
		int fifties = (money - left)/50;
		int tenies = money / 10;
		left = left / 10;
		System.out.println(fifties + left);
		this.choiceReceipt( fifties + " * €50,- and " + left + " * €10,- [A]", tenies + " * €10,- [B]");
	}

	void message(String one, String two) {
		as.clear();
		this.welcomeScreen();
		welcome.giveOutput(one);
		welcome2.giveOutput(two);
	}

	// deze methode toont de welcome messages en wordt gebruikt als de user 3 keer
	// een foute pin invoert en als tevens homescreen
	public void redirected() {
		this.welcome.giveOutput(message);
		this.welcome2.giveOutput(message2);
	}

	// deze methode neemt de grootte van het scherm en bepaald waar de developer de
	// label precies in het midden de label neerzet
	public int centerLabel() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.middle = screenSize.width / 2;
		int point = this.middle - (550 / 2);
		this.middle -= 175;
		return point;
	}

	// hier in worden de label geinitialiseerd en geplaats op de gewilde hoogte
	public void textWelcome() {
		position = new Point(this.centerLabel(), 100);
		position2 = new Point(this.centerLabel(), 150);
		welcome = new DisplayText("stuff", this.position);
		welcome2 = new DisplayText("other-stuff", this.position2);
		welcome.giveOutput(message);
		welcome2.giveOutput(message2);
	}

	// hierin worden de meest gebruikte Strings geinitialiseerd
	public void setDisplayText() {
		// error output when account does not exist
		this.error = "The account does not exist!";
		this.error2 = "You will be redirected to the login screen";
		// welcome screen output
		this.message = "Welcome to the HR Bank!";
		this.message2 = "Please insert your card: ";
		// pin screen output
		this.pinMessage = "Please enter your pin: ";
		// blocked account
		this.blocked = "The account is blocked!";

	}

	// deze methode print in zowel de labels als de terminal dat de opgegeven
	// account niet bestaat
	public void errorScreen() {
		this.welcome.giveOutput(this.error);
		this.welcome2.giveOutput(this.error2);
		// System.out.println(this.error);
		// System.out.println(this.error2);
	}

	// de methode zet de snelkeuze menu neer en toegevoegd aan een arraylist
	public void menu() {
		as.clear();
		as.add(welcome);
		as.add(welcome2);
		welcome.giveOutput("Welcome to the Monopoly Bank!");
		welcome2.giveOutput("Choose your action: ");
		this.bCheck = new ScreenButton("Check [A]", new Point(middle, 300));
		this.bWithdraw = new ScreenButton("Withdraw [B]", new Point(middle, 350));
		this.bfast = new ScreenButton("Fast Choice [C]", new Point(middle, 400));
		this.bexit = new ScreenButton("Exit [#]", new Point(middle, 450));
		as.add(bCheck);
		as.add(bWithdraw);
		as.add(bfast);
		as.add(bexit);
	}

	// deze methode laat de user toe zijn balans te bekijken
	public void checkAccount(String iban, String pin) {
		as.clear();
		as.add(welcome);
		as.add(welcome2);
		int balance = bank.getBalance(iban, pin);
		welcome.giveOutput("Your balance is: ");
		welcome2.giveOutput(String.valueOf(balance));
		return;
	}

	public void withdraw() {
		as.clear();
		as.add(welcome);
		as.add(welcome2);

		welcome.giveOutput("Withdraw amount of: ");
		welcome2.giveOutput("Options: ");

		this.take20 = new ScreenButton("20 [1]", new Point(middle, 250));
		this.take50 = new ScreenButton("50 [2]", new Point(middle, 280));
		this.take100 = new ScreenButton("100 [3]", new Point(middle, 310));
		this.take200 = new ScreenButton("200 [4]", new Point(middle, 340));
		this.specifyAmount = new ScreenButton("Other [A]", new Point(middle, 370));
		this.bexit = new ScreenButton("Exit [#]", new Point(middle, 400));
		this.bBack = new ScreenButton("Back [*]", new Point(middle, 430));

		as.add(take200);
		as.add(take100);
		as.add(take50);
		as.add(take20);
		as.add(specifyAmount);
		as.add(bexit);
		as.add(bBack);

	}

	void fastScreen() {
		this.message("Please make a choice", "");
		this.take20 = new ScreenButton("20 [1]", new Point(middle, 250));
		this.take50 = new ScreenButton("50 [2]", new Point(middle, 300));
		this.take100 = new ScreenButton("100 [3]", new Point(middle, 350));

		as.add(take100);
		as.add(take50);
		as.add(take20);
	}

	public void choiceReceipt(String top, String bottom) {
		yes = new ScreenButton(top, new Point((middle), 300));
		no = new ScreenButton(bottom, new Point((middle), 350));
		// voegen de buttons toe aan de container
		as.add(yes);
		as.add(no);
	}

	public void addScreenPad() {
		welcome.giveOutput(pinMessage);
		welcome2.giveOutput(keys);
		this.choiceReceipt("CLEAR [*]", "EXIT [#]");

	}

	// display die wordt getoond als de userpin evenlang is als de pin die is
	// ingevoerd
	public void validatingPin() {
		welcome.giveOutput("Checking pin...");
		welcome2.giveOutput(" ");
	}

	// display die wordt getoond als de user een geblokkeerde account invoerd
	public void blocked() {
		as.clear();
		as.add(welcome);
		as.add(welcome2);
		welcome.giveOutput(blocked);
		welcome2.giveOutput(" ");
	}

	String getCard() {
		String get = serial.getData();
		String read = "";
		if (get != null && get != "") {
			if(get.contains("~~")) {
				read = get.substring(2, 16);
				System.out.println(read);
				return read;
			}
		}
		return null;
	}
	
	String getPin() {
		String get = serial.getData();
		String read = "";
		if(get != null) {
			if(get.contains("$$")) {
				read += get.charAt(2);
				return read;
			}
		}
		return null;
	}
	
	void sendData(String b10, String b50, String iban, String amount) {
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy");
		SimpleDateFormat time = new SimpleDateFormat("HHmm");
		String datum = date.format(today);
		String tijd = time.format(today);
		String card = iban.substring(iban.length() - 4);
		String send = b10;
		send += b50 + "-"+ card + "-" + datum + "-" + tijd + "-" + amount;
		serial.write(send);
	}

	void ownAmount() {
		this.message("Please input amount thy wish", "€ " + amount);
		this.choiceReceipt("Confirm [#]", "Back [*]");
	}

	public void doTransaction() throws InterruptedException {
		System.out.println("This is just a test");
		this.welcomeScreen();
		this.redirected();

		// this.bank.connect();
		// this.bank.disconnect();
		String card = null;
		String sessionCard = null;
		while (sessionClient == false) {
			while(card == null) {
				String cardTemp = this.getCard();
				if (cardTemp != null) {
					card = cardTemp;
				}
			}
			System.out.println(card);
			if (card != null) {
				sessionClient = bank.getIban(card);

				if (!sessionClient) {
					this.errorScreen();
					Thread.sleep(1000);
					this.redirected();
				}else {
					sessionCard = card;
					break;
				}
				
				if(bank.checkIbanBlocked()) {
					this.message("The account is blocked", "Please contact the bank!");
					Thread.sleep(500);
					sessionClient = false;
					return;
				}
				
				card = null;
			}
		}

		int userPin = 4;
		
		String pin = null;
		this.attempts = 0;
		while (attempts != 3) {
			this.addScreenPad();
			tempPin = "";
			while (userPin != tempPinLength) {
				while(pin == null) {
					pin = this.getPin();
				}
				if (pin != null) {
					
					if(pin.equals("*")) {
						System.out.println("I AM HERE!!");
						if(!tempPin.isEmpty() && !tempPin.isBlank() && tempPin != null && tempPin.length() > 0) {
							String cut = tempPin;
							tempPin = cut.substring(0, tempPin.length() - 1);
							cut = keys;
							keys = cut.substring(0, keys.length() - 1);
							System.out.println("stuff: " + tempPin);
							System.out.println(tempPin.length() + " length");
							pin = "";
						}
					}else if(pin.equals("#")) {
						System.out.println("NOW HERE!!");
						sessionClient = false;
						tempPin = null;
						this.message("Exiting", "...");
						Thread.sleep(500);
						f.dispose();
						this.createFrame();
						return;
					}
					
					welcome2.giveOutput(keys);
					System.out.println(pin);
					tempPin += pin;
					System.out.println(tempPin);
					tempPinLength = tempPin.length();
					pin = null;
				}

				if (tempPinLength != 0) {
					while (keys.length() != tempPinLength) {
						keys += "X";
						welcome2.giveOutput(keys);
					}
				}
				if (tempPinLength > userPin) {
					System.out.println("Something went wrong");
					as.clear();// resetten de container
					tempPinLength = 0;// resetten de lengte
					tempPin = "";// resetten de pin
					keys = "";// resetten de keys
					return;// stappen uit de doTransaction method
				}
			}

			if (bank.checkPin(tempPin)) {// als de pin hetzelfde is
				tempPinLength = 0;
				keys = "";
				this.validatingPin();// tonen deze scherm om de user te laten wachten
				Thread.sleep(1000);
				break;// zorgt ervoor dat er uit de while loop wordt gestapt
			} else {
				this.message("Incorrect Password", "Please try again");
				Thread.sleep(750);
				attempts += 1;// tellen 1 op bij attempts
				this.tempPin = "";// resetten de tempPin
				tempPinLength = 0;// resetten de temporary lengte
				keys = "";// resetten de keys
				as.clear();// clearen de container
				as.add(welcome);// voeg de label toe
				as.add(welcome2);// ^^
			}
		}

		if (attempts == 3) {
			bank.blackList();
			this.blocked();
			sessionCard = null;
			sessionClient = false;
			Thread.sleep(1000);
			as.clear();
			this.attempts = 0;
			f.dispose();
			this.createFrame();
			return;
		}
		boolean endSession = false;// eindigen de sessie als de user heeft opgenomen

		while (endSession == false) {

			as.clear();
			this.menu();
			while (action == null) {
				action = this.getPin();
			}

			if (action != null) {

				if (action.equals("A")) {
					this.checkAccount(sessionCard, tempPin);
					Thread.sleep(1000);
					action = null;
					as.clear();
					continue;
				} else if (action.equals("B")) {
					this.withdraw();
					while (choice == null) {
						choice = this.getPin();
						if (choice != null) {
							if (choice.equals("1")) {
								choice = "20";
								bank.withdraw(choice);
								wrong = !check;
							} else if (choice.equals("2")) {
								choice = "50";
								check = bank.withdraw(choice);
								wrong = !check;
							} else if (choice.equals("3")) {
								choice = "100";
								check = bank.withdraw(choice);
								wrong = !check;
							} else if (choice.equals("4")) {
								choice = "200";
								check = bank.withdraw(choice);
								wrong = !check;
							} else if (choice.equals("*")) {
								action = null;
								choice = null;
								as.clear();
								break;
							}else if(choice.equals("#")) {
								this.message("Exiting...", "");
								endSession = true;
								Thread.sleep(500);
								continue;
							}else if (choice.equals("A")) {
								this.ownAmount();
								String get = null;
								while (get != "#") {
									get = this.getPin();
									if (get != null) {
										if (!get.equals("*") && !get.equals("#") && !get.equals("A") && !get.equals("B")
												&& !get.equals("C") && !get.equals("D")) {
											amount += get;
											this.ownAmount();
											// System.out.println(amount);
										} else if (get.equals("#")) {
											// System.out.println("inside");
											if (!amount.contains("#") && !amount.contains("null") && amount != "" && amount != null && amount.endsWith("0")) {
												System.out.println("/" + amount + "/");
												choice = amount;
												check = bank.withdraw(choice);
												if(!check) {
													this.message("You don't have enough credit", "Please wait and try again");
													Thread.sleep(500);
													amount = "";
													choice = null;
													choice = "A";
													this.ownAmount();
													continue;
												}
												amount = "";
												break;
											} else {
												this.message("Please input a valid amount", "Your amount has to end with a 0");
												Thread.sleep(500);
												amount = "";
												choice = null;
												choice = "A";
												this.ownAmount();
												continue;
											}
										} else if (get.equals("*")) {
											choice = null;
											amount = "";
											this.withdraw();
											break;
										}
									}
								}
							}else {
								this.message("Chosen option is not optional", "Please choose again");
								Thread.sleep(1000);
								choice = null;
								this.withdraw();
							}
						}
					}
				} else if (action.equals("C")) {
					// System.out.println("Inside C");
					choice = "70";
					check = bank.withdraw(choice);
					as.clear();
					if(check) {
						this.welcomeScreen();
						this.message("Withdrawing €" + choice , "Have a nice day!");
						action = null;
						tempPin = "";
						sessionClient = false;
						endSession = true;
					}else {
						this.message("Unable to get the money!", "Please contact the bank!");
						endSession = true;
						sessionClient = false;
						action = null;
						tempPin = "";
					}
					return;
				} else if (action.equals("#")) {
					welcome.giveOutput("");
					welcome2.giveOutput("");
					welcome.giveOutput("Exiting...");
					endSession = true;
					tempPin = null;
					keys = "";
					Thread.sleep(500);
					continue;
				}else {
					action = null;
					choice = null;
					Thread.sleep(50);
					this.menu();
					continue;
				}
				
				if(wrong) {
					this.message("SOMETHING WENT WRONG", "PLEASE CONTACT THE BANK");
					Thread.sleep(750);
					this.as.clear();
					this.withdraw();
					choice = null;
					wrong = false;
				}

				if (check == true) {
					this.message("Please select preffered bill options ", "Tens or Fifties?");
					this.options(choice);
					boolean bill = false;
					String answer = null;
					while(!bill) {
						while(answer == null) {
							String check = null;
							check = this.getPin();
							if(check != null) {
								answer = check;
							}
						}
						
						if(answer == "A") {
							this.message("Redirecting...", "");
							bill = true;
							continue;
						}else if(answer == "B") {
							this.message("Redirecting...", "");
							bill = true;
							continue;
						}
						
					}
					this.message("Would you like a receipt", "");
					this.choiceReceipt("Yes [A]", "No [Any other key]");
					answer = null;
					while (answer == null) {
						String receipt = null;
						receipt = this.getPin();
						if (receipt != null) {
							answer = receipt;
						}
					}

					if (answer == "A") {
						this.createReceipt(choice);
						int i = 0;
						while(i != 5) {
							sendData("X", "X", sessionCard, amount);
						}
						welcome.giveOutput("Now dispensing $ " + choice);
						welcome2.giveOutput("Please take your card and your cash!");
						Thread.sleep(2000);
						as.clear();
						endSession = true;
					} else {
						this.message("Now dispensing $ " + choice, "Please take your card and cash!");
						welcome.giveOutput("Now dispensing $ " + choice);
						Thread.sleep(2000);
						as.clear();
						endSession = true;
					}
				}
			}
		}
		
		sessionCard = null;
		action = null;
		attempts = 0;
		endSession = false;
		tempPin = null;
		choice = null;
		this.sessionClient = false;
		as.clear();
		return;
	}
}