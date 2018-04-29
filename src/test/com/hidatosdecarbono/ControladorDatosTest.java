package com.hidatosdecarbono;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControladorDatosTest {
    private ControladorDatos dataCtrl;

    @BeforeEach
    void setUp() {
        dataCtrl = new ControladorDatos();
    }

    @Test
    void userRegisteredWhenNewUsernameAndPassword() throws InvalidUserException {
        dataCtrl.registraUsuario("nico123","1111");
        String dbPass = dataCtrl.getDb().getUsersMap().get("nico123").password;
        assertEquals("1111",dbPass);
    }

    @Test
    void shouldThrowExceptionWhenRegisterExistingUsername(){
        assertThrows(InvalidUserException.class, ()->
        {
            dataCtrl.registraUsuario("nico123","1111");
            dataCtrl.registraUsuario("nico123","2222");
        });
    }

    @Test
    void shouldLogInWhenCorrectUsernameAndPassword() throws InvalidUserException {
        String username="nico123";
        String password="1111";
        dataCtrl.registraUsuario(username,password);
        assertFalse(dataCtrl.loginUsuario(username,"badpassword"));
        assertTrue(dataCtrl.loginUsuario(username,password));
    }

    @Test
    void shouldGetAllTheUsersRegistered() throws InvalidUserException {
        String[] usernames= new String[]{"Aleix", "Eloi", "Nico"};
        String password= "ASD";
        dataCtrl.registraUsuario(usernames[0],password);
        dataCtrl.registraUsuario(usernames[1],password);
        dataCtrl.registraUsuario(usernames[2],password);
        String[] allUsers = dataCtrl.getAllUsers();
        boolean sameUsers=(allUsers.length==usernames.length);
        int i=0;
        while(sameUsers && i<usernames.length){
            sameUsers=false;
            for (int j = 0; j < allUsers.length ; j++) {
                if(usernames[i]==allUsers[j])sameUsers=true;
            }
            i++;
        }
        assertTrue(sameUsers);
    }


}