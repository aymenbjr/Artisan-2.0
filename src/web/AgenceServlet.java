package web;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebFault;

import dao.AdminImpl;
import dao.ClientImpl;
import dao.CommandeImpl;
import dao.IAdmin;
import dao.IClient;
import dao.ICommande;
import dao.IPanier;
import dao.IVoyage;
import dao.PanierImpl;
import dao.VoyageImpl;
import entities.Admin;
import entities.Client;
import entities.Commande;
import entities.Panier;
import entities.Voyage;

/**
 * Servlet implementation class AgenceServlet
 */
@WebServlet({"/login_client","/admin_commande" ,"/admin_voyage" ,"/login" ,"/ajout_voyage" ,"/supprimer_commande" ,"/supprimer_voyage" 
	,"/deconnection" ,"/Acceuil","/registration","/recherche2" ,"/recherche" ,"/voyage" , "/default", "/type", "/contact", "/details" 
	, "/utilisateur_commande", "/paiment1","/paiment2","/panier","/supprimer_panier" })
public class AgenceServlet extends HttpServlet {
	private IAdmin adminDao;
	private IVoyage voyageDao;
	private ICommande commandeDao;	
	private IClient clientDao;
	private IPanier panierDao;
	
	HttpSession session;
	
    @Override
	public void init() throws ServletException {
		adminDao=new AdminImpl();
		voyageDao=new VoyageImpl();
		commandeDao= new CommandeImpl();
		clientDao= new ClientImpl();
		panierDao=new PanierImpl();
		//polymorphisme
    }

    public AgenceServlet(){
    	super();
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		if(request.getServletPath().equals("/admin_commande")) {
			List<Commande> commande =  commandeDao.ListCommande();
			request.setAttribute("commande", commande ); 
			request.getRequestDispatcher("WEB-INF/admin.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/admin_voyage")) {
			List<Voyage> voyages =  voyageDao.ListVoyage();
			request.setAttribute("voyages", voyages );
			request.getRequestDispatcher("WEB-INF/adminVoyage.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/panier")) {
			String idVoyage = request.getParameter( "id_c" );
			String idUtilisateur = request.getParameter( "id_u" );
			
	    	if(idVoyage != null && idUtilisateur != null) {
	    		Voyage v2=voyageDao.getVoyage(idVoyage);
	    		String dest2=v2.getDestination();
	    		String imag2=v2.getImage();
	    		String typ2=v2.getType();
	    		Panier p=new Panier(Integer.parseInt(idVoyage),Integer.parseInt(idUtilisateur),dest2,imag2,typ2);
	    		panierDao.addPanier(p);
	    		request.setAttribute("succes", "Votre voyage est ajouter au panier!");
	    		int nbrCmd = panierDao.CountPanier(Integer.parseInt(idUtilisateur));
				request.setAttribute("nbrCmd", nbrCmd);
				List<Panier> panier =  panierDao.ListPanier(idUtilisateur);
				request.setAttribute("idUtilisateur", idUtilisateur);
				request.setAttribute("panier", panier );
	    		request.getRequestDispatcher("WEB-INF/panier.jsp").forward(request, response);
	    	} else if(idVoyage == null && idUtilisateur != null){
			List<Panier> panier =  panierDao.ListPanier(idUtilisateur);
			request.setAttribute("idUtilisateur", idUtilisateur);
			request.setAttribute("panier", panier );
			int nbrCmd = panierDao.CountPanier(Integer.parseInt(idUtilisateur));
			request.setAttribute("nbrCmd", nbrCmd);
			request.getRequestDispatcher("WEB-INF/panier.jsp").forward(request, response);
	    	}else{
	    		request.setAttribute("erreur", "Une erreur est survenue");
	    		request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
	    	}
		}
		
		
		if(request.getServletPath().equals("/supprimer_panier")) {
			String idVoyage = request.getParameter( "id_c" );
			String idUtilisateur = request.getParameter( "id_u" );
	    	if(idVoyage != null && idUtilisateur != null) {
	    		panierDao.deletePanier(Integer.parseInt(idVoyage),Integer.parseInt(idUtilisateur));
	    		request.setAttribute("succes", "Votre Voyage a �t� supprimer du panier!");
	    		List<Panier> panier =  panierDao.ListPanier(idUtilisateur); 
				request.setAttribute("panier", panier );
				int nbrCmd = panierDao.CountPanier(Integer.parseInt(idUtilisateur));
				request.setAttribute("nbrCmd", nbrCmd);
				request.getRequestDispatcher("WEB-INF/panier.jsp").forward(request, response);
	    	}
	    	request.setAttribute("erreur", "Une erreur est survenue");
			request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
		}
		
		
		if(request.getServletPath().equals("/Acceuil")) {
			Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
			if(utilisateurConnecte != null) {
				request.setAttribute("idUtilisateur", utilisateurConnecte.getId_client());
				int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
				request.setAttribute("nbrCmd", nbrCmd);
			}
			
			request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/login")) {
			
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/registration")) {
			
			request.getRequestDispatcher("WEB-INF/registration.jsp").forward(request, response);
		}

		if(request.getServletPath().equals("/login_client")) {
//			if(session!=null){
//				request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
//			}else{
			request.getRequestDispatcher("WEB-INF/loginClient.jsp").forward(request, response);
		}
//	}
		
		if(request.getServletPath().equals("/ajout_voyage")) {
			
			request.getRequestDispatcher("WEB-INF/addVoyage.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/paiment2")) {
			
			request.getRequestDispatcher("WEB-INF/paiment2.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/default")) {
			
			request.getRequestDispatcher("WEB-INF/default.jsp").forward(request, response);
		}
		
		/**
	     * on se rederige vers la liste des voyages si aucun id produit n'est renseign�,
	     * sinon on affiche le detail de ce produit
	     */
		
		if(request.getServletPath().equals("/type")) {
			Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
				if(utilisateurConnecte != null) {
					int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
					request.setAttribute("nbrCmd", nbrCmd);
				}
			String typeVoyage = request.getParameter( "type_c" );
	    	if(typeVoyage == null) {
	    		
	    		request.getRequestDispatcher("WEB-INF/type.jsp").forward(request, response);
	    	} else {
	    		List<Voyage> voyages = voyageDao.ListParType(typeVoyage);
	    		request.setAttribute("voyages", voyages);				
	    		request.getRequestDispatcher("WEB-INF/voyage.jsp").forward(request, response);
	    	}
		}
		
		if(request.getServletPath().equals("/contact")) {
			
			request.getRequestDispatcher("WEB-INF/contact.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/recherche2")) {
			
			request.getRequestDispatcher("WEB-INF/rechercheAv.jsp").forward(request, response);
		}
		
		if(request.getServletPath().equals("/supprimer_commande")) {
			try {
				String idCommande = request.getParameter( "id_c" );
				commandeDao.deleteCommande(Integer.parseInt(idCommande));
				//chargement de liste des commandes
				List<Commande> commande =  commandeDao.ListCommande();
				request.setAttribute("commande", commande );  
				request.setAttribute("succes", "votre commande a �t� bien supprim�");	
			} catch (Exception e) {
				request.setAttribute("erreur", "Une erreur est survenue lors de la suppression du commande ");	
			} finally {
				request.getRequestDispatcher("WEB-INF/admin.jsp").forward(request, response);
			}	
		}
		
		if(request.getServletPath().equals("/supprimer_voyage")) {
			try {
				String idVoyage = request.getParameter( "id_c" );
				voyageDao.deleteVoyage(Integer.parseInt(idVoyage));
				//chargement de liste des voyages
				List<Voyage> voyages =  voyageDao.ListVoyage();
				request.setAttribute("voyages", voyages ); 
				request.setAttribute("succes", "votre voyage a �t� bien supprim�");	
			} catch (Exception e) {
				request.setAttribute("erreur", "Une erreur est survenue lors de la suppression du voyage ");	
			} finally {
				request.getRequestDispatcher("WEB-INF/adminVoyage.jsp").forward(request, response);
			}	
		}
	
		
		if(request.getServletPath().equals("/deconnection")){
			
			session = request.getSession();
			session.invalidate();
			
			request.setAttribute("succes", "Vous vous �tes bien d�connect�(e)");
			
			// Redirection vers la page d'acceuil
			response.sendRedirect("http://localhost:8080/Artisan/Acceuil");
		}
		
		if(request.getServletPath().equals("/voyage")) {

    		Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
			if(utilisateurConnecte != null) {
				request.setAttribute("idUtilisateur", utilisateurConnecte.getId_client());
				int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
				request.setAttribute("nbrCmd", nbrCmd);
			}
			String idControleur = request.getParameter( "id_c" );
	    	if(idControleur == null) {
				List<Voyage> voyages =  voyageDao.ListVoyage();		
				request.setAttribute("voyages", voyages);		
				request.getRequestDispatcher("WEB-INF/voyage.jsp").forward(request, response);
			} else {		
				Voyage voyageAffichee = voyageDao.getVoyage(idControleur);  
				
				request.setAttribute("voyage", voyageAffichee);			
				request.getRequestDispatcher("WEB-INF/details.jsp").forward(request, response);
			}
		}
		
		if(request.getServletPath().equals("/utilisateur_commande")){

			String idVoyage = request.getParameter( "id_c" );
			String idUser = request.getParameter( "id_u" );
	    	if(idVoyage != null && idUser != null) {
	    		// creation de la commande
	    		Voyage V1 = voyageDao.getVoyage(idVoyage);
	    		String Vdest = V1.getDestination();
	    		String Vtype = V1.getType();
	    		String Vdate = V1.getDate();
	    		int Vduree = V1.getDuree();
	    		Commande nouvelleCommande = new Commande(Integer.valueOf(idUser), Integer.valueOf(idVoyage),Vdest,Vtype,Vdate,Vduree);
	    		commandeDao.addCommande(nouvelleCommande);
	    		
	    		// redirection vers la page de payment			
				request.getRequestDispatcher("WEB-INF/paiment.jsp").forward(request, response);
	    	} else {
					// Redirection vers la page d'acceuil en cas d'erreur
	    		request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
				}
	    		
	    	}
		}
		

	


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			if(request.getServletPath().equals("/login_client")) {

				String exceptionContent = "Une erreur est survenue lors de l'authentification";
				try{
					String nom = request.getParameter("nom");
					String password = request.getParameter("password");
					boolean status = false;
					status=clientDao.login(nom, password);
					// utilisateur existe dans la base
					if(status){
						// cr�e la session
						session = request.getSession();
						// on met dedans l'utilisateur authentifi� maintenant qu'on sait qu'il existe dans notre base de donn�es
						Client utilisateur = clientDao.getClient(nom, password);
						session.setAttribute("session", utilisateur);
						request.setAttribute("succes", "Bienvenue "+ utilisateur.getNom() + "! Vous vous �tes bien authentifi�");
						Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
						if(utilisateurConnecte != null) { 
							int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
							request.setAttribute("nbrCmd", nbrCmd);
						}
						request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
				
					}else {
						exceptionContent = "login / mdp incorrect";
						throw new Exception(exceptionContent);
					}
				}catch (Exception e) {
					request.setAttribute("erreur", exceptionContent);	
					request.getRequestDispatcher("WEB-INF/loginClient.jsp").forward(request, response);
				
				}
	
		}
			
			//----------------------------------------------------------------------------
			
		if(request.getServletPath().equals("/login")) {
				String exceptionContent = "Une erreur est survenue lors de l'authentification";
				try{
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					boolean status = false;
					status=adminDao.login(email, password);
					// utilisateur existe dans la base
					if(status){
						// cr�e la session
						session = request.getSession();
						// on met dedans l'utilisateur authentifi� maintenant qu'on sait qu'il existe dans notre base de donn�es
						Admin utilisateur = adminDao.getAdmin(email, password);
						session.setAttribute("session", utilisateur);
						request.setAttribute("succes", "Bienvenue "+ utilisateur.getNom() + "! Vous vous �tes bien authentifi�");
						List<Commande> commande =  commandeDao.ListCommande();
						request.setAttribute("commande", commande ); 
						request.getRequestDispatcher("WEB-INF/admin.jsp").forward(request, response);
				
					}else {
						exceptionContent = "login / mdp incorrect";
						throw new Exception(exceptionContent);
					}
				}catch (Exception e) {
					request.setAttribute("erreur", exceptionContent);	
					request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
				
				} 
	}
		
		//----------------------------------------------------------------------------
			if(request.getServletPath().equals("/ajout_voyage")) {
				try {
					String destination = request.getParameter("destination");
					String type = request.getParameter("type");
					String date = request.getParameter("date");
					int duree = Integer.valueOf(request.getParameter("duree"));
					// image par defaut ( temporaire)
					String image  = "./img/product01.jpg";
					double prix = Double.valueOf(request.getParameter("prix"));	
					String theme = request.getParameter("theme");
					String hebergement = request.getParameter("hebergement");	
					
					Voyage nouveauVoyage = new Voyage(destination, type, date, duree , prix, image, theme, hebergement);
					voyageDao.addVoyage(nouveauVoyage);
					
					// chargement de la liste des Voyages
					List<Voyage> voyage =  voyageDao.ListVoyage();
					request.setAttribute("voyages", voyage);	
					request.setAttribute("succes", "votre voyage a �t� bien ajout�");	
				} catch (Exception e) {
					request.setAttribute("erreur", "Une erreur est survenue. Veuillez verifier que vous avez bien saisi les champs du formulaire ");	
				} finally {
					request.getRequestDispatcher("WEB-INF/adminVoyage.jsp").forward(request, response);
				}	
		}
			
		//----------------------------------------------------------------------------	
			
			if(request.getServletPath().equals("/registration")) {
				try {
					// Restitution des donn�es depuis le formulaire
					String nom = request.getParameter("nom");
					String email = request.getParameter("email");
					String motDePasse = request.getParameter("mdp");
					String telephone = request.getParameter("telephone");
					
					
					Client client = new Client(nom,email ,motDePasse ,telephone);		
					clientDao.addClient(client); 
					Client client2 = clientDao.getClient(nom, motDePasse);
					session = request.getSession();
					session.setAttribute("session", client2);
					request.setAttribute("succes", "Bienvenue "+ client2.getNom() + "! Vous vous �tes bien authentifi�");
					Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
					if(utilisateurConnecte != null) {
						int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
						request.setAttribute("nbrCmd", nbrCmd);
					}
					request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
					
				} catch (Exception e) {
					request.setAttribute("erreur", "Une erreur est survenue. Veuillez verifier que vous avez bien saisi les shamps du formulaire ");	
					request.getRequestDispatcher("WEB-INF/registration.jsp").forward(request, response);
				} 
			}
			
			//----------------------------------------------------------------------------	
			
			if(request.getServletPath().equals("/recherche")){
				
				String recherche = request.getParameter("recherche");
				if(recherche.equals("")){
					String option = request.getParameter("option");	
					if(option=="0"){
						response.sendRedirect("http://localhost:8080/Artisan/Acceuil");
					}
					else if(option.equals("1")){
						response.sendRedirect("http://localhost:8080/Artisan/type?type_c=TURQUIE");
					}
					else if(option.equals("2")){
						response.sendRedirect("http://localhost:8080/Artisan/type?type_c=EUROPE");
					}
					else if(option.equals("3")){
						response.sendRedirect("http://localhost:8080/Artisan/type?type_c=ASIE");
					}
					
				}else{
				List<Voyage> voyage =  voyageDao.ListParDest(recherche);
				request.setAttribute("voyages", voyage);
				if(voyage.size()==0){
					request.setAttribute("erreur", "La destination: '"+recherche+"' n'est pas disponible dans notre Catalogue");
					Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
					if(utilisateurConnecte != null) {
						int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
						request.setAttribute("nbrCmd", nbrCmd);
					}
					request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
					
				}else
				request.getRequestDispatcher("WEB-INF/voyage.jsp").forward(request, response);
				
				
				}
			}
			
			if(request.getServletPath().equals("/contact")) {
				
//				 String USER_NAME = "Kargo.GmbH";  // GMail user name (just the part before "@gmail.com")
//				 String PASSWORD = "Bachelor."; // GMail password
//				 String RECIPIENT = "aymenbjuni@gmail.com";
//
//				        String from = USER_NAME;
//				        String pass = PASSWORD;
//				        String[] to = { RECIPIENT }; // list of recipient email addresses
//				        String subject = "hadchi zin";
//				        String body = "Min eclipse ilayk!";
//
//				        voyageDao.sendFromGMail(from, pass, to, subject, body);
//				    }
//
//				    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
//				        Properties props = System.getProperties();
//				        String host = "smtp.gmail.com";
//				        props.put("mail.smtp.starttls.enable", "true");
//				        props.put("mail.smtp.host", host);
//				        props.put("mail.smtp.user", from);
//				        props.put("mail.smtp.password", pass);
//				        props.put("mail.smtp.port", "587");
//				        props.put("mail.smtp.auth", "true");
//
//				        Session session = Session.getDefaultInstance(props);
//				        MimeMessage message = new MimeMessage(session);
//
//				        try {
//				            message.setFrom(new InternetAddress(from));
//				            InternetAddress[] toAddress = new InternetAddress[to.length];
//
//				            // To get the array of addresses
//				            for( int i = 0; i < to.length; i++ ) {
//				                toAddress[i] = new InternetAddress(to[i]);
//				            }
//
//				            for( int i = 0; i < toAddress.length; i++) {
//				                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//				            }
//
//				            message.setSubject(subject);
//				            message.setText(body);
//				            Transport transport = session.getTransport("smtp");
//				            transport.connect(host, from, pass);
//				            transport.sendMessage(message, message.getAllRecipients());
//				            transport.close();
//				        }
//				        catch (AddressException ae) {
//				            ae.printStackTrace();
//				        }
//				        catch (MessagingException me) {
//				            me.printStackTrace();
//				        }
				request.setAttribute("succes", "Email envoy�!");
				request.getRequestDispatcher("WEB-INF/acceuil.jsp").forward(request, response);
			}
			
			//----------------------------------------------------------------------------	
			
			if(request.getServletPath().equals("/recherche2")) {
				try{
					String destination = request.getParameter("destination");
					String type = request.getParameter("type");
					String dureeMin = request.getParameter("dureeMin");
					double c=Double.parseDouble(dureeMin);
					String dureeMax = request.getParameter("dureeMax");
					double b=Double.parseDouble(dureeMax);
					String budge = request.getParameter("budge");
					List<Voyage> voyage =  voyageDao.RechercheAvancee(destination,type,dureeMin,dureeMax ,budge);		
					request.setAttribute("voyages", voyage);
					Client utilisateurConnecte = (Client) request.getSession().getAttribute("session");
					if(utilisateurConnecte != null) {
						int nbrCmd = panierDao.CountPanier(utilisateurConnecte.getId_client());
						request.setAttribute("nbrCmd", nbrCmd);
					}
					request.getRequestDispatcher("WEB-INF/voyage.jsp").forward(request, response);
				}catch (Exception e) {
					request.setAttribute("erreur", "Une erreur est survenue. Veuillez verifier que vous avez bien saisi les champs du formulaire ");	
					request.getRequestDispatcher("WEB-INF/rechercheAv.jsp").forward(request, response);
				}
				
			}
			
			//----------------------------------------------------------------------------	
			
			
}
}
