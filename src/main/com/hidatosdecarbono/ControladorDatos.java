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

    public boolean loginUsuario(String username,String password){
        return db.getUsersMap().get(username).password.equals(password);
    }

    public String[] getAllUsers(){
        String[] allUsers = new String[db.getUsersMap().size()];
        db.getUsersMap().keySet().toArray(allUsers);
        return allUsers;
    }
    

}