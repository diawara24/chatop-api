package com.openclassroom.chatopapi.constantes;

public class SecurityConstant {
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'avez pas la permission d'accéder à cette page";

    public static final String ACCOUNT_LOCKED = "Vôtre compte a été bloqué. Merci de contacter l'administration";
    public static final String METHOD_IS_NOT_ALLOWED = "Cette méthode de requête n'est pas autorisée sur ce point de terminaison. Veuillez envoyer une demande '%s'";
    public static final String INTERNAL_SERVER_ERROR_MSG = "Une erreur s'est produite lors du traitement de la demande";
    public static final String INCORRECT_CREDENTIALS = "Nom d'utilisateur/mot de passe incorrect. Veuillez réessayer";
    public static final String ACCOUNT_DISABLED = "Votre compte a été désactivé. S'il s'agit d'une erreur, veuillez contacter l'administration";
    public static final String ERROR_PROCESSING_FILE = "Une erreur s'est produite lors du traitement du fichier";
    public static final String NOT_ENOUGH_PERMISSION = "Vous n'avez pas suffisamment d'autorisation";
    public static final String ERROR_PATH = "/error";
    public static final String[] PUBLIC_URLS ={"/auth/**","/", "/uploads/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**"};



}
