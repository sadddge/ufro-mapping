package org.ufromap.controllers;

import org.json.JSONObject;
import org.ufromap.models.Usuario;
import org.ufromap.services.IService;
import org.ufromap.services.UsuarioService;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/usuario/*")
public class UsuarioController extends BaseController<Usuario> {

    public UsuarioController(IService<Usuario> service) {
        super(new UsuarioService());
    }

    @Override
    protected Usuario mapJsonToEntity(JSONObject jsonObject) {
        return new Usuario(
                jsonObject.optInt("id",-1),
                jsonObject.optString("nombre_usuario", null),
                jsonObject.optString("correo", null),
                jsonObject.optString("contrasenia", null),
                null
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[0];
    }
}
