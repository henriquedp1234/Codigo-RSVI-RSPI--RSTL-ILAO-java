import Problem.Description.Acao;
import Problem.Description.Estado;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Henri on 19/02/2019.
 */

public class NewHeuristica2 extends Heuristica {
    double heuristic=0;
    HashMap<Estado, HashMap<Acao, Double>> Q;
    public NewHeuristica2(Map<Integer, Estado> e, double a,HashMap<Estado, HashMap<Acao, Double>> Q)
    {
        estadosFinais = e;
        heuristic=a;
        this.Q=Q;
    }
    public double calcula(Estado e) {
        int size = e.actionSize();

        double Qmin=Double.POSITIVE_INFINITY;
        for (int i = 0; i < size; i++) {//itera por todas acoes s
            Acao a = e.getAction(i);//pega ação
            if (Qmin>Q.get(e).get(a))
                Qmin= Q.get(e).get(a);
        }
        for (int i = 0; i < size; i++) {//itera por todas acoes s
            Acao a = e.getAction(i);//pega ação
            a.setQ(Qmin);


        }
        e.setCost(Qmin);

        return 1;
    }
    public double geth()
    {
        return heuristic;
    }
}

