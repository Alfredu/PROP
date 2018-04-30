package com.hidatosdecarbono;

//Registrar usuario
//Obtener hidato
//Registrar usuario
//Login Usuario


public class ControladorDatos{

    private BaseDeDatos db;

    public ControladorDatos() {
        this.db = new BaseDeDatos();;
    }

    public BaseDeDatos getDb() {
        return db;
    }


    /**
     * Crear un nuevo usuario en el sistema con el nombre de usuario y contraseña indicados
     * @param username String que contiene el nombre de usuario para el nuevo usuario.
     * @param password String que contiene la contraseña del usuario para el nuevo usuario.
     * @throws InvalidUserException En caso que el usuario ya exista.
     */
    public void registraUsuario(String username, String password) throws InvalidUserException {
        InfoUsuario infoUsuario = new InfoUsuario();
        infoUsuario.password=password;
        if(!db.getUsersMap().containsKey(username)) {
            db.getUsersMap().put(username, infoUsuario);
        }
        else throw new InvalidUserException("The username alredy exists");
    }

    /**
     * Efectua el login de un usuario a través de su nombre de usuario y password.
     * @param username String que contiene el nombre de usuario del que se quiere hacer login
     * @param password String que contiene la contraseña del usuario del que se quiere hacer login
     * @return Devuelve true si el login fue posible. Devuelve false si hubo algún error.
     */
    public boolean loginUsuario(String username,String password){
        return db.getUsersMap().get(username).password.equals(password);
    }

    /**
     * Devuelve el nombre de usuario de todos los usuarios del sistema
     * @return Un array de Strings que contiene todos los nombres de usuarios registrados
     * en el sistema.
     */
    public String[] getAllUsers(){
        String[] allUsers = new String[db.getUsersMap().size()];
        db.getUsersMap().keySet().toArray(allUsers);
        return allUsers;
    }
    

}