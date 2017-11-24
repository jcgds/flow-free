package com.squidgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.squidgames.Screens.MainMenu;

/**
 * Created by juan on 23/11/17.
 *
 * Accion que cambia la pantalla actual a un Screen recibido como parametro.
 * Creada para encadenar un cambio de pantalla a una secuencia de acciones.
 */

public class SwitchScreenAction extends Action {
    Game game;
    Screen screen;

    public SwitchScreenAction(Game game, Screen screen) {
        super();
        this.game = game;
        this.screen = screen;
    }

    @Override
    public boolean act(float delta) {
        game.setScreen(screen);
        return false;
    }
}
