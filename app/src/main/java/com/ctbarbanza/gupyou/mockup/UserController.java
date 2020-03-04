package com.ctbarbanza.gupyou.mockup;

import com.ctbarbanza.gupyou.models.Commentario;
import com.ctbarbanza.gupyou.models.User;
import com.ctbarbanza.gupyou.models.Valoracion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UserController {


    private User currentUser;

    private UserController(){
        this.generateDatos();
        this.setCurrentUser();
    }
    private static final UserController instance =  new UserController();
    public static UserController init(){
        return instance;
    }

    private HashMap<String, User> users = new HashMap<String, User>();
    private HashMap<String, List<Commentario>> comentarios  = new HashMap<String, List<Commentario>>();
    private HashMap<String, List<Valoracion>>  valoraciones = new HashMap<String, List<Valoracion>>();

    private void setCurrentUser(){
        Random random = new Random();
        Object[] values = (Object[]) users.values().toArray();
        Object obj = values[random.nextInt(values.length)];
        this.currentUser = (User)obj;
    }

    private void generateDatos(){
        this.generateUsers();
        this.generateCommentarios();
        this.generateValoraciones();
    }

    private void generateValoraciones() {
        int maxValoraciones = 15;
        for (Map.Entry<String, User> user : this.users.entrySet()) {
            int valoraciones = new Random().nextInt(maxValoraciones);
            List<Valoracion> items = new ArrayList();

            for (int i = 0; i < valoraciones; i++) {
                // TODO: vamos a usar el mismo usuario para los comentarios; pero de forma provisional.
                Valoracion item = this.generateValoracion(i, user.getValue(), user.getValue());
                items.add(item);
            }
            this.valoraciones.put(user.getValue().uid, items);
        }
    }

    private Valoracion generateValoracion(int pos, User usuario, User autor) {

        int valor = 1 + new Random().nextInt(3);

        Valoracion.TIPO_VALORACION tipo  = Valoracion.TIPO_VALORACION.getRandom();

        Valoracion item = new Valoracion();
        item.setValor(valor);
        item.setKey( tipo );
        item.setUsuario(usuario.uid);
        item.setEmisor(autor.uid);
        return item;
    }

    private void generateCommentarios() {
        int maxCommentarios = 15;
        for (Map.Entry<String, User> user : this.users.entrySet()) {
            int userCommentarios = new Random().nextInt(maxCommentarios);
            List<Commentario> items = new ArrayList();

            for (int i = 0; i < userCommentarios; i++) {
                // TODO: vamos a usar el mismo usuario para los comentarios; pero de forma provisional.
                Commentario item = this.generateCommentario(i, user.getValue(), user.getValue());
                items.add( item );
            }
            this.comentarios.put(user.getValue().uid, items);
        }
    }

    private Commentario generateCommentario(int pos, User usuario, User autor) {
        Commentario item = new Commentario();
        item.setMsg("Comentario Para usuario ");
        item.setUsuario(usuario.uid);
        item.setEmisor(autor.uid);
        return item;
    }

    private void generateUsers() {
        int totalUsers = 10;
        for (int i = 0; i < totalUsers; i++) {
            User user = this.generateUser(i);
            this.users.put(user.uid, user);
        }
    }

    private User generateUser(int pos){

        int randomUi = new Random().nextInt(100);

        User user = new User();
        String name  = "Usuario - "+pos;
        String nick  = "User"+pos;
        String uid   = ""+pos+"_"+randomUi;
        String image = "ic_user";

        return user;
    }


    public User getCurrentUser(){
        return this.currentUser;
    }


    public List<Commentario> getComentarios(String uid){
        if (this.comentarios.containsKey(uid)){
            return this.comentarios.get(uid);
        }
        return new ArrayList<>();
    }

    public List<Valoracion> getValoraciones(String uid){
        if (this.valoraciones.containsKey(uid)){
            return this.valoraciones.get(uid);
        }
        return new ArrayList<>();
    }

    public List<User> searchUsers(String uid, String nick, String name, String facebook, String google, String twitter, String instagram){

        ArrayList<User> items = new ArrayList<>();

        for (Map.Entry<String, User> entry : this.users.entrySet()) {
            User user = entry.getValue();

            boolean cUid = user.uid.equals(uid);
            boolean cNick = user.nick.contains(nick);
            boolean cName = user.name.contains(name);
            boolean cFb = user.facebook.contains(facebook);
            boolean cG = user.google.contains(google);
            boolean cTw = user.twitter.contains(twitter);
            boolean cIns = user.instagram.contains(instagram);

            if (cUid || cNick || cName || cFb || cG || cTw || cIns){
                items.add(user);
                continue;
            }
        }
        
        return items;
    }

    public List<User> getAllUsers(){
        ArrayList<User> items = new ArrayList<>();

        for (Map.Entry<String, User> entry : this.users.entrySet()) {
            User user = entry.getValue();
            items.add(user);
        }

        return items;
    }

    public void addComentario(String uid, Commentario com){
        this.comentarios.get(uid).add(com);
    }

    public void addValoracion(String uid, Valoracion item){
        this.valoraciones.get(uid).add(item);
    }
}
