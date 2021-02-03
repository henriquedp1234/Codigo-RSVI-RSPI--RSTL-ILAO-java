import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Transicao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Henri on 19/02/2019.
 */

public class NewHeuristica extends Heuristica {
    double heuristic=0;
    public NewHeuristica(Map<Integer, Estado> e,double a)
    {
        estadosFinais = e;
        heuristic=a;
    }
    public double calcula(Estado e) {
        int size = e.actionSize();
        for (int i = 0; i < size; i++) {//itera por todas acoes s
            Acao a = e.getAction(i);//pega ação
            a.setQ(heuristic);


        }

e.setCost(heuristic);
        return 1;
    }
    public double geth()
    {
        return heuristic;
    }
}

