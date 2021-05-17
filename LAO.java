//Classe que possui o RSTL-ILAO* para o problema do RIO, usando recompensa.
import Problem.Description.*;
import Problem.Arquivo;

import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Iterator;

public class LAO {

    public static final int VALUE = 0;
    public double riskFactor = 0.15; //Fator de risco

    private Estado estadoInicial;
    private Map<Integer, Estado> vertices;
    private Map<Integer, Boolean> setZ;
    private Map<Integer, Boolean> setT;
    private int count;
    private Heuristica h;
    private Problem p;
    double heur=0;
    double qnormal;
    double qmeta;

    public LAO(Problem p, double qnormal,double qmeta) {
        count = 0;
        this.p = p;
        this.qnormal=qnormal;
        this.qmeta=qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        h = new NewHeuristica(p.getFinalStates(),heur);

      //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
       h.calcula(estadoInicial);
        vertices.put(estadoInicial.hashCode(), estadoInicial);
    }

    public void executa(int dynammicAlgorithm) {
        boolean nonTerminalTip = true;
        HashMap<Estado, HashMap<Acao, Double>> Qlinha1 = new HashMap<>();//map para guardar o Qanterior.
        for (Estado s : p.getEstados().values()) {

            HashMap<Acao, Double> t = new HashMap<Acao, Double>();
            int tamanho = s.actionSize();
            for (int i = 0; i < tamanho; i++) {

                t.put(s.getAction(i), s.getAction(i).getQ());
            }
            Qlinha1.put(s, t);
        }
        while (nonTerminalTip) {

            do {

                setT.clear();
                setT.put(estadoInicial.hashCode(), true);

                nonTerminalTip = depthFirstSearch(estadoInicial, Qlinha1);
            } while (nonTerminalTip);
            this.pprint(0, 0);
            if (dynammicAlgorithm == VALUE) {
                nonTerminalTip = valueIteration(Qlinha1);
            }
            this.pprint(0, 0);
        }
        this.pprint(0, 0);
    }

    public void exec_IV() {

    }

    public double maxvet(double[] valores) {
        double maior = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < valores.length; i++) {
            if (valores[i] > maior) {
                maior = valores[i];
            }
        }
        return maior;
    }

    public int maxveti(double[] valores) {
        double maior = Double.NEGATIVE_INFINITY;
        int index = 0;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] > maior) {
                maior = valores[i];
                index = i;
            }
        }
        return index;
    }



   /* public void print(Arquivo file) {
        Set<Integer> chaves = setZ.keySet();
        String[][] a = new String[5][7];
        for (Integer chave : chaves) {

            Estado e = vertices.get(chave);

            if (e.isGoal()) {
                System.out.println("Estado: " + e.getName() + " custo: " + e.getCost());
                String d = e.getName();
                a[Integer.valueOf(d.charAt(1)) - 49][Integer.valueOf(d.charAt(3)) - 49] = String.valueOf(e.getCost());
            } //else if (e.wasExpanded()) System.out.println("Estado: " + e.getName() + " acao: " + p.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            else {

                System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getQ() + "   " + e.getAction(1).getQ() + "  " + e.getAction(2).getQ() + "   " + e.getAction(3).getQ());
              //  System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
                String d = e.getName();
                a[Integer.valueOf(d.charAt(1)) - 49][Integer.valueOf(d.charAt(3)) - 49] = e.getAction(e.getBestAction()).getName();
                file.escreveArquivo("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            }
        }
        for (int i = 0; i <= 6; i++) {
            {
                for (int i2 = 0; i2 <= 4; i2++) {

                    System.out.print(a[i2][i] + "    ");
                }
                System.out.println();
            }
        }
            System.out.println("\nQuantidade de estados na politica: " + setZ.size());
            file.escreveArquivo("Quantidade de estados na politica: " + setZ.size());
            System.out.println("Quantidade de estados expandidos: " + count);
            file.escreveArquivo("Quantidade de estados expandidos: " + count);
            file.fechaArquivo();
        }*/



    public void print(Arquivo file,int a1,int b1) {
        Set<Integer> chaves = setZ.keySet();
        String[][] a = new String[b1][a1];

        for (Integer chave : chaves) {

            Estado e = vertices.get(chave);


            if (e.isGoal()) {
                System.out.println("Estado: " + e.getName() + " custo: " + e.getCost());
                String d = e.getName();
                int x = e.getX();
                int y = e.getY();
                a[x-1][y-1] = String.valueOf(e.getCost());
            } //else if (e.wasExpanded()) System.out.println("Estado: " + e.getName() + " acao: " + p.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            else {
                if (e.getAction(0)!= null&e.getAction(1)!= null&&e.getAction(2)!= null&&e.getAction(3)!= null)
                {
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " +e.getAction(0).getName() +" "+  e.getAction(0).getQ() + "   " + e.getAction(1).getName() +" "+ e.getAction(1).getQ() + "  " + e.getAction(2).getName() +" "+e.getAction(2).getQ() + "   " +e.getAction(3).getName() +" "+ e.getAction(3).getQ());}
                //  System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
       /*         else if (e.getAction(0)!= null&e.getAction(1)!= null&&e.getAction(2)!= null&&e.getAction(3)== null)
                {
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getQ() + "   " + e.getAction(1).getQ() + "  " + e.getAction(2).getQ());}
                //  System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
                else if (e.getAction(0)!= null&e.getAction(1)!= null&&e.getAction(2)== null&&e.getAction(3)== null)
                {
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getQ() + "   " + e.getAction(1).getQ());}
                //  System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());

*/
                int x = e.getX();
                int y = e.getY();
                a[x-1][y-1] = e.getAction(e.getBestAction()).getName();
                file.escreveArquivo("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());

            }


        }
        for (int i = 0; i < a1; i++) {
            {
                for (int i2 = 0; i2 < b1; i2++) {
                    if (a[i2][i]==null)
                    {
                        System.out.print("0");
                    }
                    else
                    {
                        if (a[i2][i].equals("move-N"))
                        {
                            System.out.print( "↑");
                        }
                        else   if (a[i2][i].equals("move-S"))
                        {
                            System.out.print("↓");
                        }
                        else  if (a[i2][i].equals("move-E"))
                        {
                            System.out.print("→");
                        }
                        else  if (a[i2][i].equals("move-W"))
                        {
                            System.out.print("←");
                        }
                    }
                    // System.out.print(a[i2][i] + "    ");

                }
                System.out.println();
            }
        }
        System.out.println("\nQuantidade de estados na politica: " + setZ.size());
        file.escreveArquivo("Quantidade de estados na politica: " + setZ.size());
        System.out.println("Quantidade de estados expandidos: " + count);
        file.escreveArquivo("Quantidade de estados expandidos: " + count);
        file.fechaArquivo();
    }







    public void pprint(int xMax, int yMax) {
        this.pprint(xMax, yMax, false);
    }

    public void pprint(int xMax, int yMax, Boolean force) {
        if (!force) {
            return;
        }
        if (xMax == 0) {
            xMax = 5;
        }
        if (yMax == 0) {
            yMax = 14;
        }
        System.out.println("");
        for (int y = 1; y <= yMax; y++) {
            for (int x = 1; x <= xMax; x++) {
                String s = "x" + x + "y" + y;
                int sHash = s.hashCode();
                System.out.print("[" + s + "|" + sHash + "] ");

                Estado e = this.p.getEstado(s + "");
                if (e.wasExpanded()) {
                    System.out.print("*e*");
                } else {
                    System.out.print("   ");
                }

                int ba = e.getBestAction();
                if (ba > -1) {
                    Acao a = e.getAction(ba);
                    System.out.print("(" + a.getName() + ")");
                } else {
                    System.out.print("( null )");
                }
                System.out.print(String.format("%.4f", e.getCost()));

                System.out.print("\t");
            }
            System.out.println("");
        }
    }

    // Busca em profundidade ate um no nao expandido
    private boolean depthFirstSearch(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        boolean expanded = false;
        if (estadoCorrente.wasExpanded()) {
            boolean firstExpanded = false;
            Acao a = estadoCorrente.getAction(estadoCorrente.getBestAction());
            LinkedList<Transicao> t = a.getTransitions();
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                Transicao trans = it.next();
                Estado e = trans.getState();

                if (!setT.containsKey(e.hashCode()) && !e.isGoal()) {
                    setT.put(e.hashCode(), true);
                    firstExpanded = depthFirstSearch(e, Qlinha1);
                    if (firstExpanded) {
                        expanded = firstExpanded;
                    }
                }
            }
        } else {
            expande(estadoCorrente, Qlinha1);
            return true;
        }

        minUtility(estadoCorrente, Qlinha1);
        this.pprint(0, 0);

        return expanded;
    }

    private void expande(Estado estadoExp, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        count++;
        System.out.println("\nExpande: " + estadoExp.getName());

        int size = estadoExp.actionSize();
        for (int i = 0; i < size; i++) {
            Acao a = estadoExp.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                Transicao trans = it.next();
                Estado e = trans.getState();

                if (!vertices.containsKey(e.hashCode())) {
                    vertices.put(e.hashCode(), e);
                    //e.setCost(h.calcula(e));
                    h.calcula(e);
                }
            }
        }
        estadoExp.setExpanded();
        minUtility(estadoExp, Qlinha1);
        this.pprint(0, 0);
    }

    private boolean valueIteration(HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
     //   double epsilon = Math.pow(10, -16);0.00001
        double epsilon =0.00001;
        double error;
        LinkedList<Estado> visited = new LinkedList<Estado>();

        do {
//            HashMap<Estado, HashMap<Acao, Double>> Qlinha1 = new HashMap<>();//
//            for (Estado s :p.getEstados().values()) {
//
//                HashMap<Acao, Double> t = new HashMap<Acao, Double>();
//                int tamanho =s.actionSize();
//                for (int i=0;i<tamanho;i++) {
//
//                    t.put(s.getAction(i),s.getAction(i).getQ() );
//                }
//                Qlinha1.put(s, t);
//            }
            setZ.clear();
            visited.add(estadoInicial);
            setZ.put(estadoInicial.hashCode(), true);
            error = 0;

            // Busca em profundidade
            while (!visited.isEmpty()) {
                Estado s = visited.remove();

                if (!s.wasExpanded()) {
                    return true;
                }

                double errorI = update(s, Qlinha1);
                error = Math.max(errorI, error);
                Acao a = s.getAction(s.getBestAction());
                List<Transicao> t = a.getTransitions();
                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                    Transicao trans = it.next();
                    Estado e = trans.getState();

                    if (!setZ.containsKey(e.hashCode()) && !e.isGoal()) {
                        setZ.put(e.hashCode(), true);
                        visited.add(e);
                    }
                }
            }

        } while (error > epsilon);

        return false;
    }

  /*  private boolean policyIterationRSMDP(HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
         double epsilon = Math.pow(10, -16);
         double error;
         LinkedList<Estado> visited = new LinkedList<Estado>();

         do {
             setZ.clear();
             visited.add(estadoInicial);
             setZ.put(estadoInicial.hashCode(), true);
             error = 0;

             // Busca em profundidade
             while (!visited.isEmpty()) {
                 Estado s = visited.remove();

                 if (!s.wasExpanded()) {
                     return true;
                 }

                 double errorI = update(s, Qlinha1);
                 error = Math.max(errorI, error);
                 Acao a = s.getAction(s.getBestAction());
                 List<Transicao> t = a.getTransitions();
                 for (Iterator<Transicao> it = t.iterator(); it.hasNext();) {
                     Transicao trans = it.next();
                     Estado e = trans.getState();

                     if (!setZ.containsKey(e.hashCode()) && !e.isGoal()) {
                         setZ.put(e.hashCode(), true);
                         visited.add(e);
                     }
                 }
             }
         } while (error > epsilon);

         return false;
     }*/

    private double update(Estado s, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        double oldCost = s.getCost();
        minUtility(s, Qlinha1);
        double newCost = s.getCost();
        double errorI = Math.abs(oldCost - newCost);

        return errorI;
    }

    private void minUtility2(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        double sinal = -1.0 * Math.signum(this.riskFactor);
        sinal = 1.0;
        double novoCusto = Double.POSITIVE_INFINITY * sinal;
        int bestAction = -1;
        int size = estadoCorrente.actionSize();
        for (int i = 0; i < size; i++) {
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            double costEst = 0;
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                Transicao trans = it.next();
                Estado e = trans.getState();
                double prob = trans.getProbA();

                costEst += prob * e.getCost();//pega V
                //costEst += prob*e.getCost() * Math.exp(riskFactor);
            }
            //costEst += a.getCost();
            costEst *= Math.exp(this.riskFactor * a.getCost());
            if (costEst * sinal < novoCusto * sinal) {
                novoCusto = costEst;
                bestAction = i;
            }
        }
        estadoCorrente.setCost(novoCusto);
        estadoCorrente.setBestEdge(bestAction);
    }

    private void minUtility(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        int bestAction = -1;
        double qlinha = 0;
        double Q = 0;
        double somatudo = 0;
        double Qmax = Double.NEGATIVE_INFINITY;
        //double Qmax = Double.POSITIVE_INFINITY;
        int size = estadoCorrente.actionSize();
        HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            somatudo = 0;
            // qcomparadorQlinha = t.get(i).getState().isGoal() == true ? 1 : Double.NEGATIVE_INFINITY;// auxilia na verificação do max Q(s",a)
       /*    for (Iterator<Transicao> it = t.iterator(); it.hasNext();) {//verifica QMAX
               Transicao trans =it.next();
               int tamanho=trans.getState().actionSize();
               for (int i2=0;i2<tamanho;i2++)
               {
               Acao a2 = trans.getState().getAction(i2);
                double qcomparadorQlinha2 = a2.getQ();//recupera Q(s',a)
                if (qcomparadorQlinha < qcomparadorQlinha2) {
                    qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o maximo dos Q(s',a)
                }
               }
            }
*/
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições estado s
            {
                Transicao trans = it.next();
                int tamanho = trans.getState().actionSize();
                double qcomparadorQlinha = Double.NEGATIVE_INFINITY;
                qcomparadorQlinha = trans.getState().isGoal() == true ?  qmeta : Double.NEGATIVE_INFINITY;// auxilia na verificação do max Q(s",a) ou retorna o valor da heuristica.

                for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.
                    Acao a2 = trans.getState().getAction(i2);
                    double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                        if(Double.compare(qcomparadorQlinha,qcomparadorQlinha2)<0)
                        {
                        qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o maximo dos Q(s',a)
                    }
                }
                double prob = trans.getProbA();
               /* if (trans.getState().isGoal() == true) {
                   System.out.println(1212);
                }
*/
                qlinha = a.getQ(); //recupera Q anterior da HashMap de Qi-1(s,a)
                if (Qlinha1 != null) {
                    Q = Qlinha1.get(estadoCorrente).get(a);//qanterior
                } else {
                    Q = qlinha;
                }
                //  double delta = a.getCost() + (p.getDiscount() * (qcomparadorQlinha)) - Q;
                double X = calculaX(a.getCost(), p.getDiscount(), qcomparadorQlinha, Q, p.getK());
                somatudo += prob * X;
            }
            somatudo = somatudo * p.getTamanhoPasso();
            double calculaQsa = Q + somatudo;
           // if (Qmax < calculaQsa) {
           // if (Qmax > calculaQsa) {//pega o Qmax
            if(Double.compare(Qmax,calculaQsa)<0)
            {
                Qmax = calculaQsa;
                bestAction = i;
            }
            guardaQantesAtualizar.put(a, calculaQsa); //guarda todos os Q para atualizar no fim.

        }
        HashMap<Acao, Double> t = new HashMap<Acao, Double>();
        System.out.println(estadoCorrente.getName());
        for (Map.Entry<Acao, Double> entry : guardaQantesAtualizar.entrySet()) {//atualiza Qs do estado corrente
            Acao key = entry.getKey();
            Double value = entry.getValue();
            key.setQ(value);
            t.put(key,value);
            System.out.print(key.getName()+":"+value);
        }
        System.out.println();
        Qlinha1.put(estadoCorrente,t);
        estadoCorrente.setCost(Qmax);//V
        estadoCorrente.setBestEdge(bestAction);
    }

    public double calculaX(double recompensaSA, double fatorDesconto, double qislinhaa, double qisa, double k) {
        double x = 0;
        x = recompensaSA + (fatorDesconto * qislinhaa) - qisa;
        if (x > 0) {
            x = (1 - k) * x;
        } else {
            x = (1 + k) * x;
        }
        return x;
    }

    public void superValueIteration() {
        System.out.println("\n\nsuperValueIteration"); //Cost
        Map<Integer, Estado> estados = this.p.getEstados();

        Map<Integer, Double> V0 = new HashMap<Integer, Double>();
        Map<Integer, Acao> V0a = new HashMap<Integer, Acao>();
        Map<Integer, Double> Vi = new HashMap<Integer, Double>(); //Iteracao anterior
        Map<Integer, Acao> Via = new HashMap<Integer, Acao>();
        Map<Integer, Double> Vii = new HashMap<Integer, Double>(); //Iteracao atual
        Map<Integer, Acao> Viia = new HashMap<Integer, Acao>();

        //Varre os estados e monta V0
        for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
            Integer k = it.next();
            //System.out.println(k.toString()); //Key
            Estado s = estados.get(k);
            //System.out.print( s.getName() ); //State Name

            double[] costs = new double[s.actionSize()];

            V0.put(k, Double.POSITIVE_INFINITY);
            V0a.put(k, null);
            Vi.put(k, Double.POSITIVE_INFINITY);
            Via.put(k, null);
            Vii.put(k, Double.POSITIVE_INFINITY);
            Viia.put(k, null);

            for (int i = 0; i < s.actionSize(); i++) {
                Acao a = s.getAction(i);
                costs[i] = Math.exp(this.riskFactor * a.getCost());
                if (costs[i] < V0.get(k)) {
                    V0.replace(k, costs[i]);
                    //V0.replace(k, 1000.0);
                    V0a.replace(k, a);
                }
            }

            if (s.actionSize() < 1) {
                V0.replace(k, 1.0);
            }
            Vi.replace(k, V0.get(k));
            Via.replace(k, V0a.get(k));
            //System.out.print(" ");
            //System.out.println( V0.get(k) ); //Cost
        }

        double epsilon = 1e-16; //Erro maximo
        double erro = Double.NEGATIVE_INFINITY; //Erro da iteraçao
        double erroS = Double.NEGATIVE_INFINITY; //Erro do estado S

        do {
            erro = Double.NEGATIVE_INFINITY;
            erroS = Double.NEGATIVE_INFINITY;
            //System.out.println( "\n\nIteração X:" ); //Cost
            for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
                Integer k = it.next();
                erroS = 0.0;
                Estado s = estados.get(k);
                //System.out.print( s.getName() ); //State Name

                Vii.replace(k, Double.POSITIVE_INFINITY); //Obtem a melhor acao para o estado
                Viia.replace(k, null);
                if (s.actionSize() < 1) {
                    Vii.replace(k, 1.0);
                }
                for (int i = 0; i < s.actionSize(); i++) {
                    Acao a = s.getAction(i);
                    List<Transicao> t = a.getTransitions();
                    double costAction = 0.0;
                    for (Iterator<Transicao> ita = t.iterator(); ita.hasNext(); ) {
                        Transicao trans = ita.next();
                        Estado e = trans.getState();
                        double myCost = Math.exp(this.riskFactor * a.getCost());
                        costAction += myCost * trans.getProbA() * Vi.get(e.hashCode());
                    }

                    if (costAction < Vii.get(k)) { //Max para beta negativo e max para beta positivo
                        Vii.replace(k, costAction);
                        Viia.replace(k, a);
                        s.setBestEdge(i);
                    }
                    //Math.signum(d)
                    //System.out.print(" ");
                    //System.out.print(a.getName());
                    //System.out.print(" ");
                    //System.out.println( costAction ); //Cost
                }
                //Calcula o erro do estado (e o maior erro dos estados)
                erroS = Math.abs(Vii.get(k) - Vi.get(k));
                if (!s.isGoal()) {
                    erro = Math.max(erroS, erro);
                }
                //System.out.print( "Vi(s): " ); System.out.print( Vii.get(k) );
                //System.out.print( " Vi-1(s): " ); System.out.print( Vi.get(k) );
                //System.out.print( " Erro S: " );
                //System.out.println( erroS );
            }

            //Substitui o V anterior e
            for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
                Integer k = it.next();
                Vi.replace(k, Vii.get(k));
                Via.replace(k, Viia.get(k));
            }
            //System.out.print("\n\t ERRO: ");
            //System.out.println( erro ); //Cost
        } while (erro > epsilon);

        System.out.println("\n\nCONVERGIU!!! Segue politica resultante"); //Cost
        double maior = Double.NEGATIVE_INFINITY;
        //Substitui o V anterior e
        for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
            Integer k = it.next();
            Estado s = estados.get(k);
            System.out.print(s.getName()); //State Name
            System.out.print(" - ");
            Acao a = Viia.get(k);
            if (a != null) {
                System.out.print(a.getName());
            } else {
                System.out.print("goal-state");
            }
            System.out.print(" -> ");
            System.out.println(Vii.get(k));
            maior = Math.max(maior, Vii.get(k));
        }

        System.out.print("MAIOR VALOR: ");
        System.out.println(maior);
        System.out.println("FIM"); //State Name
    }

    public void superValueIterationLOG() {
        System.out.println("\n\nsuperValueIterationLOG"); //Cost
        Map<Integer, Estado> estados = this.p.getEstados();

        Map<Integer, Double> V0 = new HashMap<Integer, Double>();
        Map<Integer, Acao> V0a = new HashMap<Integer, Acao>();
        Map<Integer, Double> Vi = new HashMap<Integer, Double>(); //Iteracao anterior
        Map<Integer, Acao> Via = new HashMap<Integer, Acao>();
        Map<Integer, Double> Vii = new HashMap<Integer, Double>(); //Iteracao atual
        Map<Integer, Acao> Viia = new HashMap<Integer, Acao>();

        //Varre os estados e monta V0
        for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
            Integer k = it.next();
            //System.out.println(k.toString()); //Key
            Estado s = estados.get(k);
            //System.out.print( s.getName() ); //State Name

            double[] costs = new double[s.actionSize()];

            V0.put(k, Double.POSITIVE_INFINITY);
            V0a.put(k, null);
            Vi.put(k, Double.POSITIVE_INFINITY);
            Via.put(k, null);
            Vii.put(k, Double.POSITIVE_INFINITY);
            Viia.put(k, null);

            for (int i = 0; i < s.actionSize(); i++) {
                Acao a = s.getAction(i);
                //System.out.print();
                costs[i] = Math.exp(this.riskFactor * a.getCost());
                if (costs[i] < V0.get(k)) {
                    V0.replace(k, costs[i]);
                    V0a.replace(k, a);
                }
            }

            if (s.actionSize() < 1) {
                V0.replace(k, 0.0);
            }
            Vi.replace(k, V0.get(k));
            Via.replace(k, V0a.get(k));
            //System.out.print(" ");
            //System.out.println( V0.get(k) ); //Cost
        }

        double epsilon = 1e-15; //Erro maximo
        double erro = Double.NEGATIVE_INFINITY; //Erro da iteraçao
        double erroS = Double.NEGATIVE_INFINITY; //Erro do estado S

        do {
            erro = Double.NEGATIVE_INFINITY;
            erroS = Double.NEGATIVE_INFINITY;
            //System.out.println( "\n\nIteração X:" ); //Cost
            for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
                Integer k = it.next();
                erroS = 0.0;
                Estado s = estados.get(k);
                //System.out.print( s.getName() ); //State Name

                Vii.replace(k, Double.POSITIVE_INFINITY); //Obtem a melhor acao para o estado
                Viia.replace(k, null);
                if (s.actionSize() < 1) {
                    Vii.replace(k, 0.0);
                }
                for (int ia = 0; ia < s.actionSize(); ia++) {
                    Acao a = s.getAction(ia);
                    List<Transicao> t = a.getTransitions();
                    double[] ki = new double[t.size()];
                    double costAction = 0.0;
                    int i = 0;
                    for (Iterator<Transicao> ita = t.iterator(); ita.hasNext(); ) {
                        Transicao trans = ita.next();
                        Estado e = trans.getState();
                        ki[i] = Math.log(trans.getProbA()) + Vi.get(e.hashCode());
                        i++;
                    }
                    double maxki = this.maxvet(ki);
                    int maxkiI = this.maxveti(ki);

                    double somaExpKi = 0.0;

                    i = 0;
                    for (Iterator<Transicao> ita = t.iterator(); ita.hasNext(); ) {
                        Transicao trans = ita.next();
                        somaExpKi += Math.exp(ki[i] - maxki);
                        i++;
                    }
                    costAction = this.riskFactor * a.getCost() + maxki + Math.log(somaExpKi);
                    if (costAction < Vii.get(k)) { //Max para beta negativo e max para beta positivo
                        Vii.replace(k, costAction);
                        Viia.replace(k, a);
                        s.setBestEdge(i);
                    }
                    //System.out.print(" ");
                    //System.out.print(a.getName());
                    //System.out.print(" ");
                    //System.out.println( costAction ); //Cost
                }
                //Calcula o erro do estado (e o maior erro dos estados)
                erroS = Math.abs(Vii.get(k) - Vi.get(k));
                if (!s.isGoal()) {
                    erro = Math.max(erroS, erro);
                }
                //System.out.print( "Vi(s): " ); System.out.print( Vii.get(k) );
                //System.out.print( " Vi-1(s): " ); System.out.print( Vi.get(k) );
                System.out.print(" Erro S: ");
                System.out.println(erroS);
            }

            //Substitui o V anterior e
            for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
                Integer k = it.next();
                Vi.replace(k, Vii.get(k));
                Via.replace(k, Viia.get(k));
            }
            //System.out.print("\n\t ERRO: ");
            //System.out.println( erro ); //Cost
        } while (erro > epsilon);

        System.out.println("\n\nCONVERGIU!!! Segue politica resultante"); //Cost
        double maior = Double.NEGATIVE_INFINITY;
        //Substitui o V anterior e
        for (Iterator<Integer> it = estados.keySet().iterator(); it.hasNext(); ) {
            Integer k = it.next();
            Estado s = estados.get(k);
            System.out.print(s.getName()); //State Name
            System.out.print(" - ");
            Acao a = Viia.get(k);
            if (a != null) {
                System.out.print(a.getName());
            } else {
                System.out.print("goal-state");
            }
            System.out.print(" -> ");
            System.out.println(Vii.get(k));
            maior = Math.max(maior, Vii.get(k));
        }

        System.out.print("MAIOR VALOR: ");
        System.out.println(maior);
        System.out.println("FIM"); //State Name
    }

}
