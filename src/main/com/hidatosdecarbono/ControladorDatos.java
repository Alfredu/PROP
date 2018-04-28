package com.hidatosdecarbono;

//Registrar usuario
//Obtener hidato
//Registrar usuario
//Login Usuario


public class ControladorDatos{

    private BaseDeDatosFake dbFake;

    public ControladorDatos() {
        this.dbFake = new BaseDeDatosFake();;
    }

    public BaseDeDatosFake getDbFake() {
        return dbFake;
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
        if(!dbFake.getUsersMap().containsKey(username)) {
            dbFake.getUsersMap().put(username, infoUsuario);
        }
        else throw new InvalidUserException("The username alredy exists");
    }

    public boolean loginUsuario(String username,String password){
        return dbFake.getUsersMap().get(username).password.equals(password);
    }

    public String[] getAllUsers(){
        String[] allUsers = new String[dbFake.getUsersMap().size()];
        dbFake.getUsersMap().keySet().toArray(allUsers);
        return allUsers;
    }
    

}