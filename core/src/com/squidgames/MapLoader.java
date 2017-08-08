package com.squidgames;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.files;


public class MapLoader {

    public static ArrayList<Mapa> load() {
        FileHandle handleCuadrados = files.internal("Mapas\\MapasCuadrados\\");
        FileHandle handleHexagono = files.internal("Mapas\\MapasHexagonales\\");

        FileHandle[] jsonHexagono= handleHexagono.list();
        FileHandle[] jsonCuadrados = handleCuadrados.list();



        ArrayList<Mapa> result = new ArrayList<Mapa>();
        for(FileHandle f: jsonCuadrados) {
            Mapa toAdd = new TableroCuadros(f);
            if (toAdd != null)
                result.add(toAdd);
        }

        for(FileHandle f: jsonHexagono) {
            Mapa toAdd = new TableroHexagonos(f);
            if (toAdd != null)
                result.add(toAdd);
        }

        return result;
    }

    /*
    public static void load2(FileHandle file) {
        JSONParser parser = new JSONParser();
        try {
            Object obj;
            try {
                obj = parser.parse(file.reader());
                Gdx.app.log("bla", obj.toString());
            } catch (ParseException e) {
                Gdx.app.log("ERROR", "PARSE EXCEPTION");
                return;
            }
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray tablero = (JSONArray) jsonObject.get("tablero");
            ArrayList<ArrayList<Hexagono>> casillas = new ArrayList<ArrayList<Hexagono>>();

            //Pasamos este JSONArray a un ArrayList para acceder mas facilmente a los datos por coordenadas
            for (int i=0; i<tablero.size();i++) {
                JSONArray row = (JSONArray) tablero.get(i);
                ArrayList<Hexagono> eq = new ArrayList<Hexagono>();
                casillas.add(eq);

                for (int j = 0; j < row.size(); j++) {
                    String content = row.get(j).toString();
                    eq.add(new Hexagono(i,j,content));
                }
            }


            for (int i =0; i< casillas.size();i++) {
                ArrayList<Hexagono> row = casillas.get(i);
                for (int j=0; j<row.size(); j++) {
                    Hexagono current = row.get(j);
                    if (current.getValor().equals(""))
                        continue;

                    HashMap<Integer,Hexagono> map = new HashMap<Integer, Hexagono>();

                    try {
                        Hexagono l1 = casillas.get(i - 1).get(j + 1);
                        if (l1.getValor().equals(""))
                            throw  new IndexOutOfBoundsException();
                        map.put(1,l1);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(1,null);
                    }

                    try {
                        Hexagono l2 = casillas.get(i).get(j + 1);
                        if (l2.getValor().equals(""))
                            throw  new IndexOutOfBoundsException();
                        map.put(2,l2);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(2,null);
                    }

                    try {
                        Hexagono l3 = casillas.get(i + 1).get(j);
                        if (l3.getValor().equals(""))
                            throw  new IndexOutOfBoundsException();
                        map.put(3,l3);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(3,null);
                    }

                    try {
                        Hexagono l4 = casillas.get(i+1).get(j-1);
                        if (l4.getValor().equals(""))
                            throw  new IndexOutOfBoundsException();
                        map.put(4,l4);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(4,null);
                    }

                    try {
                        Hexagono l5 = casillas.get(i).get(j - 1);
                        if (l5.getValor().equals(""))
                            throw  new IndexOutOfBoundsException();
                        map.put(5,l5);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(5,null);
                    }

                    try {
                        Hexagono l6 = casillas.get(i-1).get(j);
                        if (l6.getValor().equals(""))
                            throw  new IndexOutOfBoundsException();
                        map.put(6,l6);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(6,null);
                    }

                    System.out.println(String.format("%s (%d,%d) %s",current.getValor(),current.i,current.j, map));
                    current.setVecino(map);
                }
            }



        } catch (IOException e) {

        }
    }
    */
}
