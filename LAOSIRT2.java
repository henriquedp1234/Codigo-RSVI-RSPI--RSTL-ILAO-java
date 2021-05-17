//implementação RSTL-ILAO* recompensa
import Problem.Arquivo;
import Problem.Arquivo2;
import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.Description.Transicao;
import sun.corba.EncapsInputStreamFactory;

import java.util.*;
import java.text.DecimalFormat;

public class LAOSIRT2 {

    public static final int VALUE = 0;
    public double riskFactor = 0.15; //Fator de risco

    private Estado estadoInicial;
    private Map<Integer, Estado> vertices;
    private Map<Integer, Boolean> setZ;
    private Map<Integer, Boolean> setT;
    private int count;
    private Heuristica h;
    private Problem p;
    double heur = 0;
    double qnormal;
    double qmeta;
    public HashMap<Estado, HashMap<Acao, Double>> Q;
    public HashMap<Estado, HashMap<Acao, Double>> Q2;
    public HashMap<Estado, HashMap<Acao, Double>> Q3;
    public HashMap<Estado, Double> V;
    int qtdeValueIteration = 0;
    int qtdeValueIteration2 = 0;
    long qtdeILAO = 0;
    long qtdedet = 0;
    long qtdedet2 = 0;
    long qtdeILAO2 = 0;
    long convergido = 0;
    double averageTime = 0;

    public LAOSIRT2(Problem p, double qnormal, double qmeta) {
        count = 0;
        this.p = p;
        this.qnormal = qnormal;
        this.qmeta = qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        h = new NewHeuristica(p.getFinalStates(), heur);

        //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
//        h.calcula(estadoInicial);
//        vertices.put(estadoInicial.hashCode(), estadoInicial);
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

    public void inicializaV() {
        V = new HashMap<>();
        for (Estado s : p.getEstados().values()) {//inicializar V

            //System.out.println(k.toString()); //Key
            //Estado s = estados.get(k);
            V.put(s, 0.0);
            //System.out.print( s.getName() ); //State Name
        }
    }

    public void print(Arquivo file, int a1, int b1) {
        Set<Integer> chaves = setZ.keySet();
        String[][] a = new String[b1][a1];

        for (Integer chave : chaves) {

            Estado e = vertices.get(chave);


            if (e.isGoal()) {
                System.out.println("Estado: " + e.getName() + " custo: " + e.getCost());
                String d = e.getName();
                int x = e.getX();
                int y = e.getY();
                a[x][y] = String.valueOf(e.getCost());
            } //else if (e.wasExpanded()) System.out.println("Estado: " + e.getName() + " acao: " + p.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            else {
                if (e.getAction(0) != null & e.getAction(1) != null && e.getAction(2) != null && e.getAction(3) != null) {
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getName() + " " + e.getAction(0).getQ() + "   " + e.getAction(1).getName() + " " + e.getAction(1).getQ() + "  " + e.getAction(2).getName() + " " + e.getAction(2).getQ() + "   " + e.getAction(3).getName() + " " + e.getAction(3).getQ());
                }
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
                a[x][y] = e.getAction(e.getBestAction()).getName();
                file.escreveArquivo("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());

            }


        }
        for (int i = 0; i < a1; i++) {
            {
                for (int i2 = 0; i2 < b1; i2++) {
                    if (a[i2][i] == null) {
                        System.out.print("0");
                    } else {
                        if (a[i2][i].equals("move-N")) {
                            System.out.print("↑");
                        } else if (a[i2][i].equals("move-S")) {
                            System.out.print("↓");
                        } else if (a[i2][i].equals("move-E")) {
                            System.out.print("→");
                        } else if (a[i2][i].equals("move-W")) {
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

    public void print2(Arquivo file, int a1, int b1) {
        Set<Integer> chaves = setZ.keySet();
        String[][] a = new String[a1][b1];

        for (Integer chave : chaves) {

            Estado e = vertices.get(chave);


            if (e.isGoal()) {
                System.out.println("Estado: " + e.getName() + " custo: " + e.getCost());
                String d = e.getName();
                int x = e.getX();
                int y = e.getY();
                a[x][y] = String.valueOf(e.getCost());
            } //else if (e.wasExpanded()) System.out.println("Estado: " + e.getName() + " acao: " + p.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            else {
                if (e.getAction(0) != null & e.getAction(1) != null && e.getAction(2) != null && e.getAction(3) != null) {
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getName() + " " + e.getAction(0).getQ() + "   " + e.getAction(1).getName() + " " + e.getAction(1).getQ() + "  " + e.getAction(2).getName() + " " + e.getAction(2).getQ() + "   " + e.getAction(3).getName() + " " + e.getAction(3).getQ());
                }
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
                a[x][y] = e.getAction(e.getBestAction()).getName();
                file.escreveArquivo("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());

            }


        }
        for (int i = 0; i < a1; i++) {
            {
                for (int i2 = 0; i2 < b1; i2++) {
                    if (a[i][i2] == null) {
                        System.out.print("0");
                    } else {
                        if (a[i][i2].equals("move-N")) {
                            System.out.print("↑");
                        } else if (a[i][i2].equals("move-S")) {
                            System.out.print("↓");
                        } else if (a[i][i2].equals("move-E")) {
                            System.out.print("→");
                        } else if (a[i][i2].equals("move-W")) {
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

    public void printSIR(Arquivo file, int a1, int b1) {
        Set<Integer> chaves = setZ.keySet();
        String[][] a = new String[b1][a1];

        for (Integer chave : chaves) {

            Estado e = vertices.get(chave);


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
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.00001;
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

    /*private boolean policyIterationRSMDP() {
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

                 double errorI = update(s);
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
     }
 */
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
                // double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                double qcomparadorQlinha = Double.NEGATIVE_INFINITY;
                //qcomparadorQlinha = trans.getState().isGoal() == true ?  qmeta : Double.POSITIVE_INFINITY;// auxilia na verificação do max Q(s",a) ou retorna o valor da heuristica.

                for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.
                    Acao a2 = trans.getState().getAction(i2);
                    double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                    //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                    // if(Double.compare(qcomparadorQlinha,qcomparadorQlinha2)>0)//guarda  min
                    if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) < 0)//guarda  min
                    {
                        qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o min dos Q(s',a)
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
            if (Double.compare(Qmax, calculaQsa) < 0)//pega max
            // if (Double.compare(Qmax, calculaQsa) > 0)//pega min
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
            t.put(key, value);
            // System.out.print(key.getName()+":"+value);
        }
        // System.out.println();
        Qlinha1.put(estadoCorrente, t);
        estadoCorrente.setCost(Qmax);//V

        System.out.println(Qmax + "   " + estadoCorrente.getName());
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

    public void probEstado(HashMap<Estado, Acao> politica, String nomeArquivo, String diret) {
        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);
        Arquivo2 arquivoSaida2 = new Arquivo2("abc" + nomeArquivo, Arquivo2.ESCRITA, "abc" + diret);

        Map<Estado, Integer> estados2345 = new HashMap<>();

        Collection<Estado> values = this.p.getEstados().values();
        Iterator<Estado> iterator = values.iterator();
        Map<Integer, Estado> estados2 = new HashMap<>();
        int çç = 0;

        while (iterator.hasNext()) {
            Estado next = iterator.next();
            estados2.put(çç, next);
            estados2345.put(next, çç);
            çç += 1;
        }


        double[][] arrayTransições = new double[p.getEstados().size()][p.getEstados().size()];

//gera matriz de transição
        for (Iterator<Integer> it2 = estados2.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
            Integer k = it2.next();
            Estado estadoCorrente = estados2.get(k);
            int size = estadoCorrente.actionSize();

            // for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            //double somatudo = 0;
            //Acao a = estadoCorrente.getAction(i);
            //LinkedList<Transicao> t = a.getTransitions();

            double somaaaa = 0;
            // for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
            for (Iterator<Transicao> it = politica.get(estadoCorrente).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
            {
                Transicao trans = it.next();
                int position = estados2345.get(trans.getState());

                arrayTransições[k][position] += trans.getProbA();
                // somaaaa+= trans.getProbA();

            }

            //           System.out.println(somaaaa);
        }
        // }


        int a = 0;
        double vetor[][] = new double[300][p.getEstados().size()];
        double vetoraux[][] = new double[300][p.getEstados().size()];
        //  double vetor[]=new double [p.getEstados().size()];
        //double vetoraux[]=new double [p.getEstados().size()];
        Estado e[] = new Estado[p.getEstados().size()];

        for (int m = 0; m < p.getEstados().size(); m++) {
            if (estados2.get(m).getName().equals(p.getInitialState().getName())) {
                vetor[0][m] = 1.0;
                vetoraux[0][m] = 1.0;


            }
            e[m] = estados2.get(m);
        }

/*        for (int m=0;m<p.getEstados().size();m++)
        {
            if (estados2.get(m).getName().equals(p.getInitialState().getName())) {
                vetor[m]=1.0;
                vetoraux[m]=1.0;
            }
        }
*/
        //multiplicação de vetor por Matriz:
        int q = 0;
        while (q < 300) {

            for (int i1 = 0; i1 < p.getEstados().size(); i1++) {
                double soma = 0;
                for (int i2 = 0; i2 < p.getEstados().size(); i2++) {
                    if (q == 0) {
                        soma += (vetor[q][i2] * arrayTransições[i2][i1]);
                    } else if (q > 0) {
                        soma += (vetor[q - 1][i2] * arrayTransições[i2][i1]);
                    }
                }
                vetoraux[q][i1] = soma;
            }

            vetor = vetoraux;
            q++;
        }
        for (int i = 0; i < 300; i++) {
            double soma = 0;
            for (int j = 0; j < p.getEstados().size(); j++) {
                soma += vetor[i][j];
            }
            System.out.println("soma" + soma);
        }

        for (int i = 0; i < 300; i++) {
            double soma = 0;
            for (int j = 0; j < p.getEstados().size(); j++) {
                double nm = vetor[i][j];

                arquivoSaida.escreveArquivo2(j + ":" + String.valueOf(nm) + " ");
            }
            arquivoSaida.escreveArquivo("");
            System.out.println("soma" + soma);
        }


        for (int o = 0; o < p.getEstados().size(); o++) {
            double sum = 0;
            {
                for (int z = 0; z < p.getEstados().size(); z++)
                    sum = sum + arrayTransições[o][z];
            }
            System.out.println(sum);

        }
        arquivoSaida.fechaArquivo();
        double vetorSumInfectToGraf[] = new double[300];
        double vetorSumRecupToGraf[] = new double[300];

        for (int m = 0; m < 300; m++) {

            double sum = 0;
            double sum2 = 0;
            for (int z = 0; z < p.getEstados().size(); z++) {
                int guardaI = e[z].getI();
                sum += vetor[m][z] * guardaI;
                int guardaR = e[z].getR();
                sum2 += vetor[m][z] * guardaR;
            }
            vetorSumInfectToGraf[m] = sum;
            vetorSumRecupToGraf[m] = sum2;
        }
        for (int z = 0; z < vetorSumInfectToGraf.length; z++) {
            arquivoSaida2.escreveArquivo2(String.valueOf(vetorSumInfectToGraf[z]) + " ");
        }
        arquivoSaida2.escreveArquivo("");
        for (int z = 0; z < vetorSumRecupToGraf.length; z++) {
            arquivoSaida2.escreveArquivo2(String.valueOf(vetorSumRecupToGraf[z]) + " ");
        }

        arquivoSaida2.fechaArquivo();


    }


    public void iterterationValue(Double minError, int x, int y) {
        Map<Integer, Estado> estados = this.p.getEstados();
        Q2 = new HashMap<>();
        V = new HashMap<>();
        double Q = 0;

        double qlinha = 0;
        int fdsdfs = 0;
        int z = 0;
        double auxresidual;
        for (Estado s : p.getEstados().values()) {//inicializar V

            //System.out.println(k.toString()); //Key
            //Estado s = estados.get(k);
            V.put(s, 0.0);
            //System.out.print( s.getName() ); //State Name
        }
        for (Estado s : p.getEstados().values()) {
            {//inicializar qsa
                HashMap<Acao, Double> t = new HashMap<Acao, Double>();


                for (int i = 0; i < s.actionSize(); i++) {
                    t.put(s.getAction(i), 0.0);
                }
                this.Q2.put(s, t);


            }
        }

        do {

            HashMap<Estado, HashMap<Acao, Double>> Qlinha1 = new HashMap<>();//map para guardar o Qanterior.
            for (Estado s : p.getEstados().values()) {

                HashMap<Acao, Double> t = new HashMap<Acao, Double>();
                int tamanho = s.actionSize();
                for (int i = 0; i < tamanho; i++) {

                    t.put(s.getAction(i), this.Q2.get(s).get(s.getAction(i)));
                }
                Qlinha1.put(s, t);
            }
            auxresidual = Double.MIN_VALUE;
            for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
                Integer k = it2.next();
                double residualEstados = 0.0;
                Estado estadoCorrente = estados.get(k);
                double erroVanterior = V.get(estadoCorrente);
                // double QMax = Double.POSITIVE_INFINITY;
                double QMax = Double.NEGATIVE_INFINITY;

                int size = estadoCorrente.actionSize();
                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    double somatudo = 0;
                    Acao a = estadoCorrente.getAction(i);
                    LinkedList<Transicao> t = a.getTransitions();
                    somatudo = 0;
                    if (estadoCorrente.getI() == 0) {
                        int vvv = 0;
                    }

                    for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                    {
                        Transicao trans = it.next();
                        int tamanho = trans.getState().actionSize();
                        // double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                        double qcomparadorQlinha = Double.NEGATIVE_INFINITY;
                        Q = Qlinha1.get(estadoCorrente).get(a);//qanterior

                        for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.
                            Acao a2 = trans.getState().getAction(i2);
                            //double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                            double qcomparadorQlinha2 = Qlinha1.get(trans.getState()).get(a2);
                            //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                            if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) < 0)//guarda  min
                            {
                                qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o min dos Q(s',a)
                            }
                        }
                        double prob = trans.getProbA();
               /* if (trans.getState().isGoal() == true) {
                   System.out.println(1212);
                }
*/
                        // qlinha = a.getQ(); //recupera Q anterior da HashMap de Qi-1(s,a)


                        // Q = qlinha;

                        //  double delta = a.getCost() + (p.getDiscount() * (qcomparadorQlinha)) - Q;

                        double X = calculaX(a.getCost(), p.getDiscount(), qcomparadorQlinha, Q, p.getK());
                        somatudo += prob * X;
                    }

                    somatudo = somatudo * p.getTamanhoPasso();
                    double calculaQsa = Q + somatudo;
                    qtdeValueIteration2++;
                    // if (Qmax < calculaQsa) {
                    // if (Qmax > calculaQsa) {//pega o Qmax
                    //  if(Double.compare(Qmax,calculaQsa)<0)//pega max
                    if (Double.compare(QMax, calculaQsa) < 0)//pega min
                    {
                        QMax = calculaQsa;
                    }
                    this.Q2.get(estadoCorrente).put(a, calculaQsa);
                }

                V.put(estadoCorrente, QMax);//guarda valores de V Qmax em V
                //System.out.println(QMax);
                residualEstados = ((V.get(estadoCorrente)) - erroVanterior) / erroVanterior;

                residualEstados = Math.abs(residualEstados);
                if (residualEstados > auxresidual)//guarda o maior residual de cada estado.
                {
                    auxresidual = residualEstados;
                }

            }


            //     System.out.println("iteraçao" + fdsdfs);
            //   fdsdfs++;


            //calcula V e Residual
            z += 1;//contador
            // System.out.println(i);
            // System.out.println(auxresidual);
            qtdeValueIteration++;
        } while (auxresidual > minError); //break;//enquanto erro é maior que o minimo

        //   imprimeV(rsmdp.V, rsmdp);
        //   System.out.println(" separa");
        //   System.out.println(" separa");
        //   System.out.println(" separa");
        //   System.out.println(" separa");
        //   System.out.println(" separa");
        //   System.out.println(" separa");
        //   imprimeQ(rsmdp.Q, rsmdp);

        // Imprime resultadoe cria arquivos

        String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2ffdafdfdfdfdfds.txt";
        String Diretorios = "desconto= " + p.getDiscount() + "/";
        //  imprimedireçãoQ3(x, y, nome, Diretorios);

        for (Estado s : p.getEstados().values()) {
            String nume = null;

            System.out.println("estado " + s.getName() + "Valor " + V.get(s));


        }


    }


    void imprimeSIRTEmFormaDeMatriz(int n, int tamanhoMAxS, String nomeArquivo, String diret,double tempo) {

        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);

        String[][] a = new String[tamanhoMAxS][tamanhoMAxS];
        String[] a1 = new String[n];
        String[] a2 = new String[n];

        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {
                a[i][j] = "-";
            }

        }
        for (int i = 1; i <= n; i++) {
            a1[i - 1] = "acao" + i;
            int i2 = i - 1;
            a2[i - 1] = "" + i2;
        }


        for (Estado s : p.getEstados().values()) {
            HashMap<Acao, Double> t = Q2.get(s);
            // HashMap<Acao, Double> t = Q2.get(s);
            arquivoSaida.escreveArquivo(s.getName() + ": " + Double.toString(V.get(s)));

            //Double.toString(s.getCost())
            double b[] = new double[n];
            for (int i = 0; i < n; i++) {
                b[i] = Double.NEGATIVE_INFINITY;
            }
            int tamanho = s.actionSize();
            int uyuyuyuy = t.size();
            for (int i = 0; i < tamanho; i++) {
                if (t.get(s.getAction(i)) != null) {

                    b[i] = t.get(s.getAction(i));

                }

            }
            double v = Double.NEGATIVE_INFINITY;//min
            // double v = Double.POSITIVE_INFINITY;//max
            String nome = null;
            for (int i = 0; i < n; i++) {
                // if (v < b[i] == true)//max
                if (v < b[i] == true)//min
                {


                    v = b[i];

                    //nome = a1[i];
                    if (i == 0) {
                        a[(int) s.getS()][(int) s.getI()] = a2[i];
                    }
                    if (i == 1) {
                        a[(int) s.getS()][(int) s.getI()] = "!";
                    }
                    if (i > 1) {
                        a[(int) s.getS()][(int) s.getI()] = a2[i - 1];
                    }


                }


            }


        }
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {

                System.out.print(a[i][j]);
                arquivoSaida.escreveArquivo2(a[i][j]);


            }
            System.out.println();
            arquivoSaida.escreveArquivo("");
        }
        arquivoSaida.escreveArquivo("Tempo médio de execução: "+tempo);
        arquivoSaida.fechaArquivo();

    }

    void imprimeSIRTEmFormaDeMatrizpolitica(int n, int tamanhoMAxS, String nomeArquivo, String diret, HashMap<Estado, Acao> politica, Problem p2,double tempo,double tempo1,double tempo2) {

        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);

        String[][] a = new String[tamanhoMAxS][tamanhoMAxS];
        String[] a1 = new String[n];
        String[] a2 = new String[n];

        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {
                a[i][j] = "-";
            }

        }
        for (int i = 1; i <= n; i++) {
            a1[i - 1] = "acao" + i;
            int i2 = i - 1;
            a2[i - 1] = "" + i2;
        }


        for (Estado s : p2.getEstados().values()) {
            //  HashMap<Acao, Double> t = Q2.get(s);
            // HashMap<Acao, Double> t = Q2.get(s);
            Acao a123456 = politica.get(s);
            // arquivoSaida.escreveArquivo(s.getName() + ": " + Double.toString(V.get(s)));

            //Double.toString(s.getCost())
            double b[] = new double[n];
          /*  for (int i = 0; i < n; i++) {
                b[i] = Double.NEGATIVE_INFINITY;
            }*/
            /*int tamanho = s.actionSize();
            int uyuyuyuy = t.size();
            for (int i = 0; i < tamanho; i++) {
                if (t.get(s.getAction(i)) != null) {

                    b[i] = t.get(s.getAction(i));

                }

            }*/
            String name123 = a123456.getName();
            for (int ll = 0; ll < a1.length; ll++) {
                if (name123.equals(a1[ll])) {
                    if (a2[ll].equals("" + 10)) {
                        a[(int) s.getS()][(int) s.getI()] = "!";
                    } else {
                        a[(int) s.getS()][(int) s.getI()] = a2[ll];
                    }
                }
            }

        }
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {

                System.out.print(a[i][j]);
                arquivoSaida.escreveArquivo2(a[i][j] + " ");


            }
            System.out.println();
            arquivoSaida.escreveArquivo("");
        }
        arquivoSaida.escreveArquivo("Tempo médio de execução total: "+tempo);
        arquivoSaida.escreveArquivo("Tempo médio de execução heurística: "+tempo1);
        arquivoSaida.escreveArquivo("Tempo médio de execução algoritmo: "+tempo2);
        arquivoSaida.fechaArquivo();

    }

    void imprimeSIRTEmFormaDeMatrizV(int n, int tamanhoMAxS, String nomeArquivo, String diret,double tempo) {

        Arquivo2 arquivoSaida = new Arquivo2("v3" + nomeArquivo, Arquivo2.ESCRITA, diret);
        Arquivo2 arquivoSaida2 = new Arquivo2("v2" + nomeArquivo, Arquivo2.ESCRITA, diret);
        String[][] a = new String[tamanhoMAxS][tamanhoMAxS];
        String[] a1 = new String[n];
        String[] a2 = new String[n];
        double[][] vfim = new double[tamanhoMAxS][tamanhoMAxS];
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {
                a[i][j] = "-";
            }

        }
        for (int i = 1; i <= n; i++) {
            a1[i - 1] = "acao" + i;
            int i2 = i - 1;
            a2[i - 1] = "" + i2;
        }


        for (Estado s : p.getEstados().values()) {
            HashMap<Acao, Double> t = Q2.get(s);
            // HashMap<Acao, Double> t = Q2.get(s);

            double b[] = new double[n];
            for (int i = 0; i < n; i++) {
                b[i] = Double.NEGATIVE_INFINITY;
            }
            int tamanho = s.actionSize();
            int uyuyuyuy = t.size();
            for (int i = 0; i < tamanho; i++) {
                if (t.get(s.getAction(i)) != null) {

                    b[i] = t.get(s.getAction(i));

                }

            }
            double v = Double.NEGATIVE_INFINITY;//min
            // double v = Double.POSITIVE_INFINITY;//max
            String nome = null;
            for (int i = 0; i < n; i++) {
                // if (v < b[i] == true)//max
                if (v < b[i] == true)//min
                {

                    DecimalFormat f = new DecimalFormat("#.##");

                    v = b[i];
                    double a47 = b[i];
                    vfim[(int) s.getS()][(int) s.getI()] = Double.parseDouble(f.format(a47).replace(',', '.'));
                    //nome = a1[i];
                    if (i == 0) {
                        a[(int) s.getS()][(int) s.getI()] = a2[i];
                    }
                    if (i == 1) {
                        a[(int) s.getS()][(int) s.getI()] = "!";
                    }
                    if (i > 1) {
                        a[(int) s.getS()][(int) s.getI()] = a2[i - 1];
                    }


                }


            }


        }
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {

                // System.out.print(a[i][j]);
                System.out.print(Double.toString(vfim[i][j]));
                arquivoSaida.escreveArquivo2(a[i][j] + " ");
                if (a[i][j].equals("-")) {
                    arquivoSaida2.escreveArquivo2("- ");
                } else {
                    arquivoSaida2.escreveArquivo2(Double.toString(vfim[i][j]) + " ");
                }
            }
            System.out.println();
            arquivoSaida.escreveArquivo("");
            arquivoSaida2.escreveArquivo("");

        }
        arquivoSaida.escreveArquivo("tempo total= " + tempo);
        arquivoSaida2.escreveArquivo("tempo total= " + tempo);
        arquivoSaida.fechaArquivo();
        arquivoSaida2.fechaArquivo();
    }


    void imprimeSIRTEmFormaDeMatriz22(int n, int tamanhoMAxS, String nomeArquivo, String diret, HashMap<Estado, Acao> politica) {

        //  Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);

        String[][] a = new String[tamanhoMAxS][tamanhoMAxS];
        String[] a1 = new String[n];
        String[] a2 = new String[n];
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {
                a[i][j] = "-";
            }

        }
        for (int i = 1; i <= n; i++) {
            a1[i - 1] = "acao" + i;
            int i2 = i - 1;
            a2[i - 1] = "" + i2;
        }


        for (Estado s : p.getEstados().values()) {
            HashMap<Acao, Double> t = Q2.get(s);
            // HashMap<Acao, Double> t = Q2.get(s);

            double b[] = new double[n];
            for (int i = 0; i < n; i++) {
                b[i] = Double.NEGATIVE_INFINITY;
            }
            int tamanho = s.actionSize();
            int uyuyuyuy = t.size();
            for (int i = 0; i < tamanho; i++) {
                if (t.get(s.getAction(i)) != null) {


                    b[i] = t.get(s.getAction(i));

                }

            }
            double v = Double.NEGATIVE_INFINITY;//min
            // double v = Double.POSITIVE_INFINITY;//max
            String nome = null;
            Acao acao = null;
            for (int i = 0; i < n; i++) {
                // if (v < b[i] == true)//max
                if (v < b[i] == true)//min
                {


                    v = b[i];
                    //nome = a1[i];
                    nome = a2[i];
                    a[(int) s.getS()][(int) s.getI()] = nome;
                    acao = s.getActionPorNome(a1[i]);

                }


            }
            politica.put(s, acao);


        }
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {

                System.out.print(a[i][j]);
                //  arquivoSaida.escreveArquivo2(a[i][j]);
            }
            System.out.println();
            //  arquivoSaida.escreveArquivo("");
        }
        //  arquivoSaida.fechaArquivo();

    }

    void putpolitica(HashMap<Estado, Acao> politica) {

        Map<Integer, Estado> estados = this.p.getEstados();

        for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
            Integer k = it2.next();
            Estado estadoCorrente = estados.get(k);
            ;
            int size = estadoCorrente.actionSize();
            for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                double somatudo = 0;
                Acao a = estadoCorrente.getAction(i);
                LinkedList<Transicao> t = a.getTransitions();
                somatudo = 0;
                double qcomparadorQlinha = Double.NEGATIVE_INFINITY;
                Acao comparator = null;
                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                {
                    Transicao trans = it.next();
                    Acao a2 = trans.getState().getAction(i);
                    //double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                    double qcomparadorQlinha2 = Q2.get(trans.getState()).get(a2);
                    //    if (qcomparadorQlinha < qcomparadorQlinha2) {

                    if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) < 0)//guarda  min
                    {
                        qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o min dos Q(s',a)
                        comparator = a2;
                    }

                }
                politica.put(estadoCorrente, comparator);


            }


        }
    }


    void imprimeSIRTEmFormaDeMatriz22(int n, int tamanhoMAxS, String nomeArquivo, String diret) {

        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);

        String[][] a = new String[tamanhoMAxS][tamanhoMAxS];
        String[] a1 = new String[n];
        String[] a2 = new String[n];
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {
                a[i][j] = "-";
            }

        }
        for (int i = 1; i <= n; i++) {
            a1[i - 1] = "acao" + i;
            int i2 = i - 1;
            a2[i - 1] = "" + i2;
        }


        for (Estado s : p.getEstados().values()) {
            HashMap<Acao, Double> t = Q2.get(s);
            // HashMap<Acao, Double> t = Q2.get(s);

            double b[] = new double[n];
            for (int i = 0; i < n; i++) {
                b[i] = Double.NEGATIVE_INFINITY;
            }
            int tamanho = s.actionSize();
            int uyuyuyuy = t.size();
            for (int i = 0; i < tamanho; i++) {
                if (t.get(s.getAction(i)) != null) {

                    b[i] = t.get(s.getAction(i));

                }

            }
            double v = Double.NEGATIVE_INFINITY;//min
            // double v = Double.POSITIVE_INFINITY;//max
            String nome = null;
            for (int i = 0; i < n; i++) {
                // if (v < b[i] == true)//max
                if (v < b[i] == true)//min
                {


                    v = b[i];
                    //nome = a1[i];
                    nome = a2[i];
                    a[(int) s.getS()][(int) s.getI()] = nome;

                }


            }


        }
        for (int i = 0; i < tamanhoMAxS; i++) {

            for (int j = 0; j < tamanhoMAxS; j++) {

                System.out.print(a[i][j]);
                arquivoSaida.escreveArquivo2(a[i][j]);
            }
            System.out.println();
            arquivoSaida.escreveArquivo("");
        }
        arquivoSaida.fechaArquivo();

    }

    public void calculaRecompensaAcumulada(int n) {
        int ç = 0;
        double recomp[] = new double[n];
        while (ç < n) {
            boolean meta = false;
            double somaRecompensaTotal = 0;
            Estado s = p.getInitialState();
            do {
                Map<Integer, Estado> estados = this.p.getEstados();
                int size = s.actionSize();
                double Qmax = Double.NEGATIVE_INFINITY;
                Acao amax = null;

                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    double somatudo = 0;
                    // Acao amax=null;
                    Acao a = s.getAction(i);
                    double qComparador = Q2.get(s).get(a);
                    if (Double.compare(Qmax, qComparador) < 0)//guarda  max
                    {
                        Qmax = qComparador;//qcomparadorQlinha guarda o min dos Q(s',a)
                        amax = a;
                    }

                }
                String name = s.getName();
                String name2 = amax.getName();
                double d = Math.random();
                // somaRecompensaTotal += Qmax;
                somaRecompensaTotal += amax.getCost();
                LinkedList<Transicao> t = amax.getTransitions();
                int tamanho = t.size();
                double[] trans = new double[tamanho];
                int i = 0;
                double somaAcumulada = 0;
                Estado tnext = null;
                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                {//  //somar todas as transições e pegar apenas a transição que da o valor do random
                    Transicao transs = it.next();
                    somaAcumulada += transs.getProbA();
                    trans[i] = transs.getProbA();
                    if (somaAcumulada > d) {
                        tnext = transs.getState();
                        break;
                    }
                    i++;
                }
                System.out.print("(Estado -> " + s.getName() + "Ação -> " + amax.getName() + ")");
                s = tnext;
                meta = s.isGoal();
                recomp[ç] = somaRecompensaTotal;

            } while (meta == false);
            ç = ç + 1;
            System.out.println();
        }

        for (int i = 0; i < n; i++) {
            System.out.println(recomp[i] + " ");
        }
        HashMap<Double, Integer> val = new HashMap<>();
        boolean vistados[] = new boolean[n];
        int qtde[] = new int[n];
        for (int i = 0; i < n; i++) {
            if (vistados[i] == false) {
                for (int k = 0; k < n; k++) {
                    if (recomp[i] == recomp[k]) {
                        vistados[i] = true;
                        vistados[k] = true;
                        qtde[i] += 1;
                        val.put(recomp[i], qtde[i]);

                    }
                }
            }


        }
        System.out.println();
        //val.forEach((k,v) -> System.out.println("key: " + k + ", value: " + v));
        val.forEach((k, v) -> System.out.println(k + " " + v));


    }

    public void policyEvaluation(Double minError, int x, int y, HashMap<Estado, Acao> politica)//Política
    {
        Map<Integer, Estado> estados = this.p.getEstados();


        double qlinha = 0;
        int fdsdfs = 0;
        int z = 0;
        double auxresidual;


        do {

            HashMap<Estado, Double> Vlinha = new HashMap<>();//map para guardar o Qanterior.
            for (Estado s : p.getEstados().values()) {


                Vlinha.put(s, V.get(s));


            }

            auxresidual = Double.MIN_VALUE;
            for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
                Integer k = it2.next();
                double residualEstados = 0.0;
                Estado estadoCorrente = estados.get(k);
                double erroVanterior = V.get(estadoCorrente);
                // int size = estadoCorrente.actionSize();
                double somatudo = 0;
                for (Iterator<Transicao> it = politica.get(estadoCorrente).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());
                    double Vanterior = Vlinha.get(estadoCorrente);//qanterior
                    double X = calculaX(politica.get(estadoCorrente).getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
                    somatudo += prob * X;
                }
                somatudo = somatudo * p.getTamanhoPasso();
                double calculaQsa = V.get(estadoCorrente) + somatudo;
                qtdeValueIteration2++;
                V.put(estadoCorrente, calculaQsa);
                residualEstados = ((V.get(estadoCorrente)) - erroVanterior) / erroVanterior;
                residualEstados = Math.abs(residualEstados);
                if (residualEstados > auxresidual)//guarda o maior residual de cada estado.
                {

                    auxresidual = residualEstados;
                }
            }

            z += 1;//contador

            qtdeValueIteration++;
        } while (auxresidual > minError); //break;//enquanto erro é maior que o minimo


    }

    public void policyEvaluationVariancia(Double minError, int x, int y, HashMap<Estado, Acao> politica, HashMap<Estado, Double> Var, Problem p, HashMap<Estado, Double> V, Problem p2, String nomeArquivo, String diret)//Política
    {
        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);
        String nomearquvo2 = " so resp" + nomeArquivo;
        String diret2 = diret + "so resp";
        Arquivo2 arquivoSaida3 = new Arquivo2(nomearquvo2, Arquivo2.ESCRITA, diret2);

        Map<Integer, Estado> estados = p2.getEstados();
        double qlinha = 0;
        int fdsdfs = 0;
        int z = 0;
        double auxresidual;
        int contador = 0;
        while (contador < 1000) {
            HashMap<Estado, Double> VarLinha = new HashMap<>();
            for (Estado s : p2.getEstados().values()) {
                VarLinha.put(s, Var.get(s));
            }

            HashMap<Estado, Double> Vlinha = new HashMap<>();//map para guardar o Qanterior.
            for (Estado s : p.getEstados().values()) {
                Vlinha.put(p2.getEstado(s.getName()), V.get(s));
            }
            auxresidual = Double.MIN_VALUE;

            for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
                Integer k = it2.next();
                Estado estadoCorrente = estados.get(k);
                int size = estadoCorrente.actionSize();
                double somatudo1 = 0;
                double somatudo2 = 0;
                double somatudo3 = 0;
                double somatudo4 = 0;
                //Somatorio p*V(s')
                for (Iterator<Transicao> it = politica.get(estadoCorrente).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());
                    somatudo1 = somatudo1 + prob * (Vtransicao);
                }
                // Somatorio P*Var(s')
                for (Iterator<Transicao> it = politica.get(estadoCorrente).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());

                    somatudo2 = somatudo2 + prob * (Var.get(trans.getState()));
                }
                // Somatorio P* V^2(s')
                for (Iterator<Transicao> it = politica.get(estadoCorrente).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());


                    somatudo3 = somatudo3 + prob * (Vtransicao * Vtransicao);
                }
                Acao a = politica.get(estadoCorrente);
                somatudo4 = (a.getCost() * a.getCost()) + (2 * a.getCost() * somatudo1) + somatudo2 + somatudo3 - Vlinha.get(estadoCorrente) * Vlinha.get(estadoCorrente);


                Var.put(estadoCorrente, somatudo4);

            }
            contador++;
        }

        z += 1;//contador

        qtdeValueIteration++;

        Estado sx = null;
        double val = 0;
        for (Estado s : p2.getEstados().values()) {
            String nume = null;


            arquivoSaida.escreveArquivo("estado " + s.getName() + "Valor " + Var.get(s));

            if (s.getName().equals("s=50.0 i=50.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=90.0 i=10.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=9.0 i=1.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=5.0 i=5.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=18.0 i=2.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=27.0 i=3.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=135.0 i=15.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }
            if (s.getName().equals("s=180.0 i=20.0 r=0.0")) {
                nume = s.getName();
                val = Var.get(s);
                sx = s;
                arquivoSaida3.escreveArquivo("estado " + sx.getName() + "Valor " + Var.get(sx));

            }

        }

//        System.out.println("estado "+ sx.getName() + "Valor " +V.get(sx));
        //    arquivoSaida.escreveArquivo("estado "+ sx.getName() + "Valor " +Var.get(sx));
        //   arquivoSaida3.escreveArquivo("estado "+ sx.getName() + "Valor " +Var.get(sx));
        arquivoSaida.fechaArquivo();
        arquivoSaida3.fechaArquivo();


    }

    public boolean policyImrprovement(HashMap<Estado, Acao> politica) {
        boolean naomudouPolitica = true;
        Map<Integer, Estado> estados = this.p.getEstados();
        // Q2 = new HashMap<>();
        //V = new HashMap<>();
        //double Q = 0;
        HashMap<Estado, Double> Vlinha = new HashMap<>();//map para guardar o Qanterior.
        for (Estado s : p.getEstados().values()) {


            Vlinha.put(s, V.get(s));


        }


        int fdsdfs = 0;
        int z = 0;

        for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
            Integer k = it2.next();
            Estado estadoCorrente = estados.get(k);
            double x = Double.MAX_VALUE;
            HashMap<Acao, Double> guardador = new HashMap<Acao, Double>();
            LinkedList<Acao> l = new LinkedList<Acao>();
            int size = estadoCorrente.actionSize();
            for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                double somatudo = 0;
                Acao a = estadoCorrente.getAction(i);
                LinkedList<Transicao> t = a.getTransitions();
                somatudo = 0;


                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());
                    double Vanterior = Vlinha.get(estadoCorrente);//qanterior
                    double X = calculaX(a.getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
                    somatudo += prob * X;
                }
                somatudo = somatudo * p.getTamanhoPasso();
                double calculaQsa = V.get(estadoCorrente) + somatudo;
                guardador.put(a, calculaQsa);
                // l.add(a);

            }
            Acao guardamelhor = null;
            //
            for (int i = 0; i < size; i++) {
                Acao a = estadoCorrente.getAction(i);

                if (x > guardador.get(a)) {
                    x = guardador.get(a);
                    guardamelhor = a;
                }


            }
            if (guardamelhor == null) {
                int a = 1;
            }
            if (guardamelhor != null) {
                if (guardamelhor.getName().equals(politica.get(estadoCorrente).getName()) == false) {
                    naomudouPolitica = false;

                }
                politica.put(estadoCorrente, guardamelhor);
            }
            //  System.out.println ("Estado " + estadoCorrente.getName() + " pol="+politica.get(estadoCorrente).getName());

               /* if (Double.compare(V.get(estadoCorrente), calculaQsa) > 0)//pega min
                {
                    if (naomudouPolitica==true) {
                        naomudouPolitica = politica.get(estadoCorrente).getName().equals(a.getName());
                    }
                    politica.put(estadoCorrente,a);

                }
                V.put(estadoCorrente,calculaQsa);

            }*/

            //
            //  if  (naomudouPolitica==false);
            //   {
            // policyEvaluation(0.1,1,1,politica);
            //  }

            //   imprimeV(rsmdp.V, rsmdp);
            //   System.out.println(" separa");
            //   System.out.println(" separa");
            //   System.out.println(" separa");
            //   System.out.println(" separa");
            //   System.out.println(" separa");
            //   System.out.println(" separa");
            //   imprimeQ(rsmdp.Q, rsmdp);

            // Imprime resultadoe cria arquivos

  /*      String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2ffdafdfdfdfdfds.txt";
        String Diretorios = "desconto= " + p.getDiscount() + "/";
        imprimedireçãoQ3(x, y, nome, Diretorios);



*/


        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        return naomudouPolitica;
    }

    public void policyIteration(HashMap<Estado, Acao> politica, int z, int x, String nomeArquivo, String diret) {
        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);
        HashMap<Estado, Double> Var = new HashMap<>();
        for (Estado s : p.getEstados().values()) {//inicializar V

            //System.out.println(k.toString()); //Key
            //Estado s = estados.get(k);
            V.put(s, 0.0);
            Var.put(s, 0.0);

            if (s.isGoal() == true) {
                V.put(s, 0.0);
                Var.put(s, 0.0);
            }
            //System.out.print( s.getName() ); //State Name
        }

        //boolean compara = true;
        // do {
        policyEvaluation(0.00000000000001, 1, 1, politica);//calcula media:
        // policyEvaluationVariancia(0.00000000000001, 1, 1, politica,Var);//calcula Variância
        // compara = policyImrprovement(politica);
        //} while (compara == false);
        Estado sx = null;
        double val = 0;
        for (Estado s : p.getEstados().values()) {
            String nume = null;

            System.out.println("estado " + s.getName() + "Valor " + V.get(s));
            if (s.getName().equals("s=50.0 i=50.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }
            if (s.getName().equals("s=90.0 i=10.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }
            if (s.getName().equals("s=9.0 i=1.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }
            if (s.getName().equals("s=5.0 i=5.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }
            if (s.getName().equals("s=18.0 i=2.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }

            if (s.getName().equals("s=27.0 i=3.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }
            if (s.getName().equals("s=180.0 i=20.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }

            if (s.getName().equals("s=135.0 i=15.0 r=0.0")) {
                nume = s.getName();
                val = V.get(s);
                sx = s;
                arquivoSaida.escreveArquivo("estado " + sx.getName() + "Valor " + V.get(sx));

            }
            /*if (s.getName().equals("s=90.0 i=10.0 r=0.0"))
            {
                nume=s.getName();
                val=V.get(s);

                sx=s;
                arquivoSaida.escreveArquivo("estado "+ sx.getName() + "Valor " +V.get(sx));

            }*/
        }

//        System.out.println("estado "+ sx.getName() + "Valor " +V.get(sx));
        // arquivoSaida.escreveArquivo("estado "+ sx.getName() + "Valor " +V.get(sx));
        arquivoSaida.fechaArquivo();
    }

    void convertePolitica(HashMap<Estado, Acao> politica, HashMap<Estado, Acao> politica2, double multiplicador, Problem p2, int tammax, HashMap<Estado, LinkedList<Estado>> abc123) {
        Collection<Estado> values = this.p.getEstados().values();
        Iterator<Estado> iterator = values.iterator();
        HashMap<Estado, LinkedList<Acao>> politica22 = new HashMap<>();

        while (iterator.hasNext()) {
            Estado next = iterator.next();
            int s = next.getS();
            int i = next.getI();
            int r = next.getR();
            double s1 = (s * multiplicador);
            double i1 = (i * multiplicador);
            double r1 = (r * multiplicador);
            long ss1 = Math.round(s1);
            long ii1 = Math.round(i1);
            long rr1 = Math.round(r1);
            boolean a = false;
            boolean b = false;
            boolean c = false;
            while (ss1 + ii1 + rr1 < tammax) {

                double getdecs = (int) Math.round((s1 - (int) s1) * 100);
                double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                if (getdecs >= getdecI && getdecs >= getdecR && a == false && ss1 < tammax) {
                    // if (getdecs >= getdecI && getdecs >= getdecR && a == false) {
                    ss1 = ss1 + 1;
                    a = true;
                } else if (getdecI >= getdecs && getdecI >= getdecR && b == false && ii1 < tammax) {
                    //else if (getdecI >= getdecs && getdecI >= getdecR && b == false) {
                    ii1 = ii1 + 1;
                    b = true;
                } else if (getdecR >= getdecs && getdecR >= getdecI && c == false && rr1 < tammax) {
                    //  else if (getdecR >= getdecs && getdecR >= getdecI && c == false) {
                    rr1 = rr1 + 1;
                    c = true;
                }
            }
            boolean a1 = false;
            boolean b1 = false;
            boolean c1 = false;
            while (ss1 + ii1 + rr1 > tammax) {


                double getdecs = (int) Math.round((s1 - (int) s1) * 100);
                double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                //if (getdecs <= getdecI && getdecs <= getdecR && a == false &&getdecs>0) {

                if (getdecs <= getdecI && getdecs <= getdecR && a1 == false) {
                    if (ss1 > 0) {
                        ss1 = ss1 - 1;
                        a1 = true;
                    } else {
                        a1 = true;

                    }
                }// else if (getdecI <= getdecs && getdecI <= getdecR && b == false&&getdecI>0) {
                else if (getdecI <= getdecs && getdecI <= getdecR && b1 == false) {
                    if (ii1 > 0) {
                        ii1 = ii1 - 1;
                        b1 = true;
                    } else {
                        b1 = true;

                    }
                } //else if (getdecR <= getdecs && getdecR <= getdecI && c == false&&getdecR>0) {
                else if (getdecR <= getdecs && getdecR <= getdecI && c1 == false) {
                    if (rr1 > 0) {
                        rr1 = rr1 - 1;
                        c1 = true;
                    } else if (a1 == true || b1 == true) {
                        rr1 = rr1 - 1;
                        c1 = true;
                    } else {
                        c1 = true;
                    }


                } else {
                    if (ss1 == 0 && ii1 == 0) {
                        rr1 = rr1 - 1;
                    } else if (ii1 == 0 && rr1 == 0) {
                        ss1 = ss1 - 1;
                    } else if (ss1 == 0 && rr1 == 0) {
                        ii1 = ii1 - 1;
                    } else if (ss1 == 0) {
                        ii1 = ii1 - 1;
                    } else if (ii1 == 0) {
                        rr1 = rr1 - 1;
                    } else if (rr1 == 0) {
                        ss1 = ss1 - 1;
                    }

                }
            }

            Acao acao1 = next.getActionPorNome(politica.get(next).getName());
            String nameacao1 = acao1.getName();
            String nome = "s=" + (double) ss1 + " i=" + (double) ii1 + " r=" + (double) rr1;

            //politica2.put(p2.getEstado(nome),politica.get(next));
            //politica2.put(p2.getEstado(nome), p2.getEstado(nome).getActionPorNome(nameacao1));


            LinkedList<Acao> action = new LinkedList<>();
            Acao acao = next.getActionPorNome(politica.get(next).getName());
            String nameacao = acao.getName();
            String nome1 = "s=" + (double) ss1 + " i=" + (double) ii1 + " r=" + (double) rr1;
            if (p2.getEstado(nome1).getS() == 0 || p2.getEstado(nome1).getI() == 0) {
                if (politica22 != null && politica22.containsKey(p2.getEstado(nome1)) == true) {

                    String name = "acao" + 1;
                    action = politica22.get(p2.getEstado(nome1));
                    action.add(p2.getEstado(nome1).getActionPorNome(name));
                    politica22.put(p2.getEstado(nome1), action);
                } else {
                    String name = "acao" + 1;
                    action.add(p2.getEstado(nome1).getActionPorNome(name));

                    politica22.put(p2.getEstado(nome1), action);
                }

            } else if (politica22 != null && politica22.containsKey(p2.getEstado(nome1)) == true) {
                action = politica22.get(p2.getEstado(nome1));
                action.add(p2.getEstado(nome1).getActionPorNome(nameacao));
                politica22.put(p2.getEstado(nome1), action);
            } else {

                action.add(p2.getEstado(nome1).getActionPorNome(nameacao));

                politica22.put(p2.getEstado(nome1), action);

            }


//Falta guardar os estados e acoes antes de atualizar a politica.


        }

        Collection<Estado> values2 = p2.getEstados().values();
        Iterator<Estado> iterator2 = values2.iterator();

        while (iterator2.hasNext()) {
            Estado next = iterator2.next();
            LinkedList<Acao> aux = new LinkedList<>();
            aux = politica22.get(next);

            int tambanho[] = new int[aux.size()];
            for (int i = 0; i < aux.size(); i++) {
                //int contador = 0;
                for (int j = 0; j < aux.size(); j++) {
                    if (aux.get(i).getName().equals(aux.get(j).getName())) {
                        tambanho[i] = tambanho[i] + 1;
                    }
                }
            }

            int getindice = 0;
            int maior = 0;
            for (int j = 0; j < aux.size(); j++) {
                if (tambanho[j] > maior) {
                    maior = tambanho[j];
                    getindice = j;
                }
            }
            politica2.put(next, aux.get(getindice));


        }


    }

    void convertePolitica123(HashMap<Estado, Acao> politica, HashMap<Estado, Acao> politica2, double multiplicador, Problem p2, int tammax, HashMap<Estado, LinkedList<Estado>> abc123) {
        Collection<Estado> values = this.p.getEstados().values();
        Iterator<Estado> iterator = values.iterator();
        HashMap<Estado, LinkedList<Acao>> politica22 = new HashMap<>();

        while (iterator.hasNext()) {
            Estado next = iterator.next();
            int s = next.getS();
            int i = next.getI();
            int r = next.getR();
            double s1 = (s * multiplicador);
            double i1 = (i * multiplicador);
            double r1 = (r * multiplicador);
            long ss1 = Math.round(s1);
            long ii1 = Math.round(i1);
            long rr1 = Math.round(r1);
            boolean a = false;
            boolean b = false;
            boolean c = false;
            while (ss1 + ii1 + rr1 < tammax) {

                double getdecs = (int) Math.round((s1 - (int) s1) * 100);
                double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                if (getdecs >= getdecI && getdecs >= getdecR && a == false && ss1 < tammax) {
                    // if (getdecs >= getdecI && getdecs >= getdecR && a == false) {
                    ss1 = ss1 + 1;
                    a = true;
                } else if (getdecI >= getdecs && getdecI >= getdecR && b == false && ii1 < tammax) {
                    //else if (getdecI >= getdecs && getdecI >= getdecR && b == false) {
                    ii1 = ii1 + 1;
                    b = true;
                } else if (getdecR >= getdecs && getdecR >= getdecI && c == false && rr1 < tammax) {
                    //  else if (getdecR >= getdecs && getdecR >= getdecI && c == false) {
                    rr1 = rr1 + 1;
                    c = true;
                }
            }
            boolean a1 = false;
            boolean b1 = false;
            boolean c1 = false;
            while (ss1 + ii1 + rr1 > tammax) {


                double getdecs = (int) Math.round((s1 - (int) s1) * 100);
                double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                //if (getdecs <= getdecI && getdecs <= getdecR && a == false &&getdecs>0) {

                if (getdecs <= getdecI && getdecs <= getdecR && a1 == false) {
                    if (ss1 > 0) {
                        ss1 = ss1 - 1;
                        a1 = true;
                    } else {
                        a1 = true;

                    }
                }// else if (getdecI <= getdecs && getdecI <= getdecR && b == false&&getdecI>0) {
                else if (getdecI <= getdecs && getdecI <= getdecR && b1 == false) {
                    if (ii1 > 0) {
                        ii1 = ii1 - 1;
                        b1 = true;
                    } else {
                        b1 = true;

                    }
                } //else if (getdecR <= getdecs && getdecR <= getdecI && c == false&&getdecR>0) {
                else if (getdecR <= getdecs && getdecR <= getdecI && c1 == false) {
                    if (rr1 > 0) {
                        rr1 = rr1 - 1;
                        c1 = true;
                    } else if (a1 == true || b1 == true) {
                        rr1 = rr1 - 1;
                        c1 = true;
                    } else {
                        c1 = true;
                    }


                } else {
                    if (ss1 == 0 && ii1 == 0) {
                        rr1 = rr1 - 1;
                    } else if (ii1 == 0 && rr1 == 0) {
                        ss1 = ss1 - 1;
                    } else if (ss1 == 0 && rr1 == 0) {
                        ii1 = ii1 - 1;
                    } else if (ss1 == 0) {
                        ii1 = ii1 - 1;
                    } else if (ii1 == 0) {
                        rr1 = rr1 - 1;
                    } else if (rr1 == 0) {
                        ss1 = ss1 - 1;
                    }

                }
            }


            //politica2.put(p2.getEstado(nome),politica.get(next));
            //politica2.put(p2.getEstado(nome), p2.getEstado(nome).getActionPorNome(nameacao1));


            Acao acao = next.getActionPorNome(politica.get(next).getName());
            String nameacao = acao.getName();

            String nome1 = "s=" + (double) ss1 + " i=" + (double) ii1 + " r=" + (double) rr1;
            LinkedList<Estado> abcdefghik = new LinkedList<>();
            abcdefghik = abc123.get(next);
            for (int yy = 0; yy < abcdefghik.size(); yy++) {
                // if (ss1==1 && i==1)
                {

                }
                // else {
                politica2.put(abcdefghik.get(yy), p2.getEstado(abcdefghik.get(yy).getName()).getActionPorNome(nameacao));
            }
        }
           /* if (p2.getEstado(nome1).getS() == 0 || p2.getEstado(nome1).getI() == 0) {
                if (politica22 != null && politica22.containsKey(p2.getEstado(nome1)) == true) {

                    String name = "acao" + 1;
                    action = politica22.get(p2.getEstado(nome1));
                    action.add(p2.getEstado(nome1).getActionPorNome(name));
                    politica22.put(p2.getEstado(nome1), action);
                } else {
                    String name = "acao" + 1;
                    action.add(p2.getEstado(nome1).getActionPorNome(name));

                    politica22.put(p2.getEstado(nome1), action);
                }

            } else if (politica22 != null && politica22.containsKey(p2.getEstado(nome1)) == true) {
                action = politica22.get(p2.getEstado(nome1));
                action.add(p2.getEstado(nome1).getActionPorNome(nameacao));
                politica22.put(p2.getEstado(nome1), action);
            } else {

                action.add(p2.getEstado(nome1).getActionPorNome(nameacao));

                politica22.put(p2.getEstado(nome1), action);

            }*/


//Falta guardar os estados e acoes antes de atualizar a politica.
/*

        }

        Collection<Estado> values2 = p2.getEstados().values();
        Iterator<Estado> iterator2 = values2.iterator();

        while (iterator2.hasNext()) {
            Estado next = iterator2.next();
            LinkedList<Acao> aux = new LinkedList<>();
            aux = politica22.get(next);

            int tambanho[] = new int[aux.size()];
            for (int i = 0; i < aux.size(); i++) {
                //int contador = 0;
                for (int j = 0; j < aux.size(); j++) {
                    if (aux.get(i).getName().equals(aux.get(j).getName())) {
                        tambanho[i] = tambanho[i] + 1;
                    }
                }
            }

            int getindice = 0;
            int maior = 0;
            for (int j = 0; j < aux.size(); j++) {
                if (tambanho[j] > maior) {
                    maior = tambanho[j];
                    getindice = j;
                }
            }
            politica2.put(next, aux.get(getindice));


        }
*/

    }


    void convertePoliticav2(HashMap<Estado, Acao> politica, HashMap<Estado, Acao> politica2, double multiplicador, Problem p2, int tammax, HashMap<Estado, LinkedList<Estado>> abc123) {
        Collection<Estado> values = p2.getEstados().values();
        Iterator<Estado> iterator = values.iterator();
        HashMap<Estado, LinkedList<Acao>> politica22 = new HashMap<>();

        while (iterator.hasNext()) {
            Estado next = iterator.next();
            int s = next.getS();
            int i = next.getI();
            int r = next.getR();
            double s1 = (s * multiplicador);
            double i1 = (i * multiplicador);
            double r1 = (r * multiplicador);
            long ss1 = Math.round(s1);
            long ii1 = Math.round(i1);
            long rr1 = Math.round(r1);
            boolean a = false;
            boolean b = false;
            boolean c = false;



            if (s == 0 || i == 0) {
                if (s == 0 && i == 0) {
                    rr1 = tammax;
                } else if (s == 0) {
                    double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    while (ii1 + rr1 + ss1 > tammax) {
                        if (getdecI <= getdecR && i > 1) {
                            ii1 = ii1 - 1;
                        } else {
                            rr1 = rr1 - 1;
                        }
                    }
                    while (ii1 + rr1 + ss1 < tammax) {
                        if (getdecI >= getdecR && i < tammax) {
                            ii1 = ii1 + 1;
                        } else {
                            rr1 = rr1 + 1;
                        }
                    }
                } else if (i == 0) {
                    double getdecs = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    if (ss1 + rr1 > tammax) {
                        if (getdecs <= getdecR && s < tammax) {
                            ss1 = ss1 - 1;
                        } else {
                            rr1 = rr1 - 1;
                        }
                    } else if (ss1 + rr1 < tammax && s > 1) {
                        if (getdecs >= getdecR) {
                            ss1 = ss1 + 1;
                        } else {
                            if (rr1 == 0) {
                                ss1 = ss1 + 1;
                            } else {
                                rr1 = rr1 + 1;
                            }
                        }
                    }

                }
            } else if (i == 1 || s == 1) {
                ////////////////////////
                if (s==1&&i==1)
                {
                    ii1=1;
                    ss1=1;
                    double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    while (ii1 + rr1 + ss1 > tammax) {
                        if (getdecI <= getdecR && i > 1) {
                            ii1 = ii1 - 1;
                        } else if (r > 1) {
                            rr1 = rr1 - 1;
                        } else {
                            if (rr1 == 0) {
                                ii1 = ii1 - 1;
                            } else {
                                rr1 = rr1 - 1;
                            }
                        }
                    }

                    while (ii1 + rr1 + ss1 > tammax && i < tammax) {
                        if (getdecI >= getdecR) {
                            ii1 = ii1 + 1;
                        } else {
                            rr1 = rr1 + 1;
                        }
                    }

                }

//////////////////////////////////////////////////////////////////////////////
                if (s == 1) {
                    ss1 = 1;

                    double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    while (ii1 + rr1 + ss1 > tammax) {
                        if (getdecI <= getdecR && i > 1) {
                            ii1 = ii1 - 1;
                        } else if (r > 1) {
                            rr1 = rr1 - 1;
                        } else {
                            if (rr1 == 0) {
                                ii1 = ii1 - 1;
                            } else {
                                rr1 = rr1 - 1;
                            }
                        }
                    }

                    while (ii1 + rr1 + ss1 > tammax && i < tammax) {
                        if (getdecI >= getdecR) {
                            ii1 = ii1 + 1;
                        } else {
                            rr1 = rr1 + 1;
                        }
                    }


                } else if (i == 1) {
                    ii1 = 1;
                    double getdecs = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    while (ss1 + rr1 + ii1 > tammax) {
                        if (getdecs <= getdecR && s > 1) {
                            ss1 = ss1 - 1;
                        } else {
                            if (rr1 == 0) {
                                ss1 = ss1 - 1;
                            } else {
                                rr1 = rr1 - 1;
                            }
                        }
                    }

                    while (ss1 + rr1 + ii1 < tammax && s < tammax) {
                        if (getdecs >= getdecR) {
                            ss1 = ss1 + 1;
                        } else {
                            rr1 = rr1 + 1;
                        }
                    }

                }
            } else {
                while (ss1 + ii1 + rr1 < tammax) {
                    double getdecs = (int) Math.round((s1 - (int) s1) * 100);
                    double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    if (getdecs >= getdecI && getdecs >= getdecR && a == false && ss1 < tammax) {
                        // if (getdecs >= getdecI && getdecs >= getdecR && a == false) {
                        ss1 = ss1 + 1;
                        a = true;
                    } else if (getdecI >= getdecs && getdecI >= getdecR && b == false && ii1 < tammax) {
                        //else if (getdecI >= getdecs && getdecI >= getdecR && b == false) {
                        ii1 = ii1 + 1;
                        b = true;
                    } else if (getdecR >= getdecs && getdecR >= getdecI && c == false && rr1 < tammax) {
                        //  else if (getdecR >= getdecs && getdecR >= getdecI && c == false) {
                        rr1 = rr1 + 1;
                        c = true;
                    }
                }
                boolean a1 = false;
                boolean b1 = false;
                boolean c1 = false;
                while (ss1 + ii1 + rr1 > tammax) {


                    double getdecs = (int) Math.round((s1 - (int) s1) * 100);
                    double getdecI = (int) Math.round((i1 - (int) i1) * 100);
                    double getdecR = (int) Math.round((r1 - (int) r1) * 100);
                    //if (getdecs <= getdecI && getdecs <= getdecR && a == false &&getdecs>0) {

                    if (getdecs <= getdecI && getdecs <= getdecR && a1 == false&&s>0) {
                        if (i==2 &&s==2)
                        {
                            rr1=rr1-1;
                        }
                        else
                            if (ss1==1)
                            {
                                rr1=rr1-1;
                            }
                           else if (ss1 > 0) {
                            ss1 = ss1 - 1;
                            a1 = true;

                        } else {
                            a1 = true;

                        }
                    }// else if (getdecI <= getdecs && getdecI <= getdecR && b == false&&getdecI>0) {
                    else if (getdecI <= getdecs && getdecI <= getdecR && b1 == false&&i>0) {
                        if (ii1 > 0) {
                            ii1 = ii1 - 1;
                            b1 = true;
                        } else {
                            b1 = true;

                        }
                    } //else if (getdecR <= getdecs && getdecR <= getdecI && c == false&&getdecR>0) {
                    else if (getdecR <= getdecs && getdecR <= getdecI && c1 == false) {
                        if (rr1 > 0) {
                            rr1 = rr1 - 1;
                            c1 = true;
                        } else if (a1 == true || b1 == true) {
                            rr1 = rr1 - 1;
                            c1 = true;
                        } else {
                            c1 = true;
                        }


                    } else {
                        if (ss1 == 0 && ii1 == 0) {
                            rr1 = rr1 - 1;
                        } else if (ii1 == 0 && rr1 == 0) {
                            ss1 = ss1 - 1;
                        } else if (ss1 == 0 && rr1 == 0) {
                            ii1 = ii1 - 1;
                        } else if (ss1 == 0) {
                            if (getdecR >= getdecI) {
                                ii1 = ii1 - 1;
                            } else {
                                rr1 = rr1 - 1;
                            }
                        } else if (ii1 == 0) {
                            if (getdecR >= getdecs) {
                                ss1 = ss1 - 1;
                            } else {
                                rr1 = rr1 - 1;
                            }

                        } else if (rr1 == 0) {
                            if (getdecI >= getdecs) {
                                ss1 = ss1 - 1;
                            } else {
                                ii1 = ii1 - 1;
                            }

                        }

                    }
                }
            }



        String nome = "s=" + (double) ss1 + " i=" + (double) ii1 + " r=" + (double) rr1;

        if (abc123 != null && abc123.containsKey(p.getEstado(nome))) {
            LinkedList<Estado> abc = abc123.get(p.getEstado(nome));
            abc.add(next);
            abc123.put(p.getEstado(nome), abc);
        } else {
            LinkedList<Estado> abc = new LinkedList<>();
            abc.add(next);
            abc123.put(p.getEstado(nome), abc);
        }

    }
}


    public void policyEvaluation2(Double minError, int x, int y, HashMap<Estado,Acao> politica)//Política
    {
        Map<Integer, Estado> estados = this.p.getEstados();


        double qlinha = 0;
        int fdsdfs = 0;
        int z = 0;
        double auxresidual;


        do {

            HashMap<Estado, Double> Vlinha = new HashMap<>();//map para guardar o Qanterior.
            for (Estado s : p.getEstados().values()) {



                Vlinha.put(s,V.get(s));


            }

            auxresidual = Double.MIN_VALUE;
            for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
                Integer k = it2.next();
                double residualEstados = 0.0;
                Estado estadoCorrente = estados.get(k);
                double erroVanterior = V.get(estadoCorrente);


                int size = estadoCorrente.actionSize();
                double somatudo = 0;

                for (Iterator<Transicao> it = politica.get(estadoCorrente).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());
                    double Vanterior = Vlinha.get(estadoCorrente);//qanterior
                    double X = calculaX(politica.get(estadoCorrente).getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
                    somatudo += prob * X;
                }
                somatudo = somatudo * p.getTamanhoPasso();
                double calculaQsa = V.get(estadoCorrente) + somatudo;
                qtdeValueIteration2++;
                V.put(estadoCorrente, calculaQsa);
                residualEstados = ((V.get(estadoCorrente)) - erroVanterior) / erroVanterior;
                residualEstados = Math.abs(residualEstados);
                if (residualEstados > auxresidual)//guarda o maior residual de cada estado.
                {

                    auxresidual = residualEstados;
                }
            }

            z += 1;//contador

            qtdeValueIteration++;
        } while (auxresidual > minError); //break;//enquanto erro é maior que o minimo





    }

    public boolean  policyImrprovement2(HashMap<Estado,Acao> politica) {
        boolean naomudouPolitica = true;
        Map<Integer, Estado> estados = this.p.getEstados();
        // Q2 = new HashMap<>();
        //V = new HashMap<>();
        //double Q = 0;
        HashMap<Estado, Double> Vlinha = new HashMap<>();//map para guardar o Qanterior.
        for (Estado s : p.getEstados().values()) {


            Vlinha.put(s, V.get(s));


        }


        int fdsdfs = 0;
        int z = 0;

        for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
            Integer k = it2.next();
            Estado estadoCorrente = estados.get(k);
            double x = Double.NEGATIVE_INFINITY;
            HashMap<Acao, Double> guardador = new HashMap<Acao, Double>();
            LinkedList<Acao> l = new LinkedList<Acao>();
            int size = estadoCorrente.actionSize();
            for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                double somatudo = 0;
                Acao a = estadoCorrente.getAction(i);
                LinkedList<Transicao> t = a.getTransitions();
                somatudo = 0;

                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());
                    double Vanterior = Vlinha.get(estadoCorrente);//qanterior
                    double X = calculaX(a.getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
                    somatudo += prob * X;
                }
                somatudo = somatudo * p.getTamanhoPasso();
                double calculaQsa = V.get(estadoCorrente) + somatudo;
                guardador.put(a, calculaQsa);
                // l.add(a);

            }
            Acao guardamelhor = null;
            //
            for (int i = 0; i < size; i++) {
                Acao a = estadoCorrente.getAction(i);
                //if (Double.compare(x, guardador.get(a)) < 0){
                if (x < guardador.get(a)) {


                    x = guardador.get(a);
                    guardamelhor= a;
                }


            }
            if (guardamelhor==null) {
                int a=1;
            }
            if (guardamelhor!=null) {
                if (guardamelhor.getName().equals(politica.get(estadoCorrente).getName()) == false &&guardador.get(guardamelhor)!=guardador.get(politica.get(estadoCorrente)) ) {
                    naomudouPolitica = false;

                }
                politica.put(estadoCorrente,guardamelhor);
            }


        }

        return naomudouPolitica;
    }
    public void policyIteration2(HashMap<Estado,Acao> politica, int z, int x) {
        for (Estado s : p.getEstados().values()) {//inicializar V

            //System.out.println(k.toString()); //Key
            //Estado s = estados.get(k);
            V.put(s, 0.0);


            if (s.isGoal() == true) {
                V.put(s, 0.0);
            }
            //System.out.print( s.getName() ); //State Name
        }
        boolean compara = true;
        do {
            policyEvaluation2(0.00000000000001, 1, 1, politica);
            compara = policyImrprovement2(politica);
        } while (compara == false);
    }
}

