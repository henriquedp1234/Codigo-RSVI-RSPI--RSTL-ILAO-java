package Problem;

import Problem.Description.*;
import Problem.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Henri on 15/10/2019.
 */
public class cenarioDenis {
    Problem r;
    Estado m[];

    public cenarioDenis(Problem rsmdp) {
        m = new Estado[3];
        r = rsmdp;
        for (int y = 0; y < 3; y++) {
            m[y] = new Estado("estado " + (y + 1),1);
        }
        criatudo();
    }

    void criatudo() {
        for (int i = 0; i < 2; i++)//todos estados
        {
            m[0].setUtilityValueLinha(1.0);
            m[2].setUtilityValueLinha(1.0);


            Acao a = new Acao("fast-action");
            // Acao a2 = new Acao("fast-action");
            Acao a3 = new Acao("slow-action");
            //Acao a4 = new Acao("slow-action");
            a.setCost(1);//custo resto
            // a2.setCost(1);
            a3.setCost(1);
            //  a4.setCost(1);

            if (i+1==3)//custo s6
            {
                a.setCost(1);

                a3.setCost(1);

            }

            if (i == 0)//s1
            {
              /*  a.setCurrentState(m[i]);
                a2.setCurrentState(m[i]);
                a3.setCurrentState(m[i]);
                a4.setCurrentState(m[i]);
                a.setSuccessorState(m[i+1]);
                a2.setSuccessorState(m[0]);
                a3.setSuccessorState(m[i+1]);
                a4.setSuccessorState(m[0]);*/
                a.adicionaTransicao(m[i + 1], 0.75, 0);
                a.adicionaTransicao(m[0], 0.25, 0);
                a3.adicionaTransicao(m[i + 1], 0.5, 0);
                a3.adicionaTransicao(m[0], 0.5, 0);


            } else {//s6
             /*   a.setCurrentState(m[i]);
                a2.setCurrentState(m[i]);
                a3.setCurrentState(m[i]);
                a4.setCurrentState(m[i]);
                a.setSuccessorState(m[i+1]);
                a2.setSuccessorState(m[i-1]);
                a3.setSuccessorState(m[i+1]);
                a4.setSuccessorState(m[i]);*/
                a.adicionaTransicao(m[i + 1], 0.75, 0);
                a.adicionaTransicao(m[i - 1], 0.25, 0);
                a3.adicionaTransicao(m[i + 1], 0.5, 0);
                a3.adicionaTransicao(m[i - 1], 0.5, 0);


            }

            /*a.setActionProbability(0.75);
            a2.setActionProbability(0.25);
            a3.setActionProbability(0.5);
            a4.setActionProbability(0.5);*/
            m[i].addAcao(a);
            m[i].addAcao(a3);
           /* m[i].addAction(a3);
            m[i].addAction(a4);*/
        }
        // printa2();
//m[6].getActions().clear();
        Acao a = new Acao("fast-action");
        Acao a2 = new Acao("slow-action");
        a.setCost(0);
        a2.setCost(0);
        //a.setActionName("fast-action");
        //a2.setActionName("slow-action");
        a.adicionaTransicao(m[2], 0, 0);
        a2.adicionaTransicao(m[2], 0, 0);
        m[2].addAcao(a);
        m[2].addAcao(a2);
      /*  a.setCurrentState(m[6]);
        a2.setCurrentState(m[6]);
        a.setSuccessorState(m[6]);
        a2.setSuccessorState(m[6]);
        m[6].addAction(a);
        m[6].addAction(a2);*/

        Map<Integer, Estado> estados = new HashMap<Integer, Estado>();
        int j = 0;
        for (int i = 0; i < 3; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {

            int l = m[i].getName().hashCode();

            estados.put(m[i].getName().trim().hashCode(), m[i]);
            j++;
        }


        r.adicionaEstados(estados);
        r.setInitialState(m[0].getName());
        r.setFinalState(m[2].getName());

    }
}