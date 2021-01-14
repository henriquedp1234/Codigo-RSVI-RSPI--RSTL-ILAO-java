package Problem;

import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;

import java.util.HashMap;
import java.util.Map;

public class cenarioNovo {
    Problem r;
    Estado m[];

    /**
     * Created by Henri on 15/10/2019.
     */
    public cenarioNovo(Problem rsmdp) {
        m = new Estado[3];
        r = rsmdp;
        for (int y = 0; y < 3; y++) {
            m[y] = new Estado("estado " + (y),1);
        }
        criatudo();
    }


        public void criatudo() {


                Acao a = new Acao("1");
                Acao a2 = new Acao("2");
                Acao a3 = new Acao("3");
                a.adicionaTransicao(m[1], 0.75, 0);
                a.adicionaTransicao(m[0], 0.25, 0);
                a2.adicionaTransicao(m[2], 0.75, 0);
                a2.adicionaTransicao(m[1], 0.25, 0);
                a3.adicionaTransicao(m[2], 1, 0);
                a.setCost(1);
                a2.setCost(1);
                a3.setCost(0);
                m[0].addAcao(a);
                m[1].addAcao(a2);
                m[2].addAcao(a3);
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

