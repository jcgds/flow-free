package com.squidgames;

/**
 * Created by juan on 23/11/17.
 */

/* TODO:
 *  Estos metodos deberian recibir un Array<Actor> que es lo que devuelve el getStage(), asi es mas
 *  general y ademas podriamos arreglar el problema del fadeOut del GameModeScreen.
 */
public interface Transitions {
    void fadeIn();
    void fadeOut();
}
