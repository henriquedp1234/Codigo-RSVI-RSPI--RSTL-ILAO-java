
//tentativa implementação LAO
import Problem.Arquivo;
import Problem.Arquivo2;
import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.Description.Transicao;

import java.util.*;
public class LAOtrue2 {

    public static final int VALUE = 0;
    public double riskFactor = 0.15; //Fator de risco
    public HashMap<Estado, HashMap<Acao, Double>> Q;
    public HashMap<Estado, HashMap<Acao, Double>> Q2;
    public HashMap<Estado, HashMap<Acao, Double>> Q3;
    public HashMap<Estado, Double> V;
    double heur = 0;
    double qnormal;
    double qmeta;
    int qtdeValueIteration = 0;
    int qtdeValueIteration2 = 0;
    long qtdeILAO = 0;
    long qtdedet = 0;
    long qtdedet2 = 0;
    long qtdeILAO2 = 0;
    long convergido = 0;
    double averageTime = 0;
    private Estado estadoInicial;
    private Map<Integer, Estado> vertices;
    private Map<Integer, Boolean> setZ;
    private Map<Integer, Boolean> setT;
    private int count;
    private Heuristica h;
    private Problem p;
    HashSet<Estado> GlinhaCopia = new HashSet<Estado>();
    public LAOtrue2(Problem p, double qnormal, double qmeta)//sem heuristica

    {
        count = 0;
        this.p = p;
        this.qnormal = qnormal;
        this.qmeta = qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        //  p.setK(0);
        //iterterationValue(0.00000000000001,x,y);
        //  valueIterationHeuristic(0.000000000000000001,x,y);
        // p.setK(1.0);
        //  h = new NewHeuristica2(p.getFinalStates(),heur,Q);
        h = new NewHeuristica(p.getFinalStates(), heur);

        //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
        h.calcula(estadoInicial);
        vertices.put(estadoInicial.hashCode(), estadoInicial);
    }

    public LAOtrue2(Problem p, double qnormal, double qmeta, int k)//2Lao

    {
        count = 0;
        this.p = p;
        this.qnormal = qnormal;
        this.qmeta = qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        //  p.setK(0);
        //iterterationValue(0.00000000000001,x,y);
        //  valueIterationHeuristic(0.000000000000000001,x,y);
        // p.setK(1.0);
        //  h = new NewHeuristica2(p.getFinalStates(),heur,Q);
        h = new NewHeuristica(p.getFinalStates(), heur);

        //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
        h.calcula(estadoInicial);
        vertices.put(estadoInicial.hashCode(), estadoInicial);
    }

    public LAOtrue2(Problem p, double qnormal, double qmeta, int x, int y)//heuristica determinizacao
    {
        count = 0;
        this.p = p;
        this.qnormal = qnormal;
        this.qmeta = qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        //  p.setK(0);
        //iterterationValue(0.00000000000001,x,y);
        Cronometro cT = new Cronometro();
        cT.start();
        valueIterationHeuristic(0.0000000000000000001, x, y);
        // p.setK(1.0);
        cT.stop();
        averageTime += cT.getTempo();

        h = new NewHeuristica2(p.getFinalStates(), heur, Q2);

        // h = new NewHeuristica(p.getFinalStates(),heur);

        //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
        h.calcula(estadoInicial);
        vertices.put(estadoInicial.hashCode(), estadoInicial);
    }

    public LAOtrue2(Problem p, double qnormal, double qmeta, int x, int y, int pranada)//heuristica iv
    {
        count = 0;
        this.p = p;
        this.qnormal = qnormal;
        this.qmeta = qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        p.setK(0.0);
        Cronometro cT = new Cronometro();

        cT.start();
        iterterationValue(0.00000000000001, x, y);
        cT.stop();
        averageTime += cT.getTempo();
        // valueIterationHeuristic(0.000000000000000001,x,y);
        // p.setK(1.0);

        h = new NewHeuristica2(p.getFinalStates(), heur, Q2);


        //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
        h.calcula(estadoInicial);
        vertices.put(estadoInicial.hashCode(), estadoInicial);
    }

    public LAOtrue2(Problem p, double qnormal, double qmeta, int x, int y, int pranada, int pranada2)//heuristica iv
    {
        count = 0;
        this.p = p;
        this.qnormal = qnormal;
        this.qmeta = qmeta;
        vertices = new HashMap<Integer, Estado>();
        setZ = new HashMap<Integer, Boolean>();
        setT = new HashMap<Integer, Boolean>();
        // Definicao da Heuristica
        //h = new Manhattan(p.getFinalStates());
        p.setK(0.0);
        Cronometro cT = new Cronometro();

        cT.start();
        iterterationValue(0.00000000000001, x, y);
        cT.stop();
        averageTime += cT.getTempo();
        // valueIterationHeuristic(0.000000000000000001,x,y);
        // p.setK(1.0);

        h = new NewHeuristicaLAO2(p.getFinalStates(), heur, Q2);


        //h = new Exponencial(p.getFinalStates(), this.riskFactor);
        // h = new ZeroOne(p.getFinalStates());


        estadoInicial = p.getInitialState();
        //estadoInicial.setCost(h.calcula(estadoInicial));
        h.calcula(estadoInicial);
        vertices.put(estadoInicial.hashCode(), estadoInicial);
    }


    public void executa2(int dynammicAlgorithm) {
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
        //inicio do código:
        while (nonTerminalTip) {

            do {

                setT.clear();
                setT.put(estadoInicial.hashCode(), true);
                //  expande(estadoInicial,Qlinha1);
                try {
                    nonTerminalTip = depthFirstSearch(estadoInicial, Qlinha1);
                    //  valueIteration(Qlinha1);
                } catch (Exception e) {
                    //      e.printStackTrace();
                }
            } while (nonTerminalTip);

            this.pprint(0, 0);

            convergido++;
            try {
                nonTerminalTip = valueIteration(Qlinha1);
            } catch (Exception e) {
                //nonTerminalTip = true;
            }

        }

    }

    public void executaLaoPolitica(int dynammicAlgorithm) throws Exception {
        HashMap<Estado, Double> Vlinha = new HashMap<>();//map para guardar o V.
        for (Estado s : p.getEstados().values()) {
            Vlinha.put(s, s.getCost());
        }

        HashMap<Estado, HashMap<Acao, Double>> Qlinha = new HashMap<>();
        for (Estado s : p.getEstados().values()) {

            HashMap<Acao, Double> t = new HashMap<Acao, Double>();
            int tamanho = s.actionSize();
            for (int i = 0; i < tamanho; i++) {

                t.put(s.getAction(i), s.getAction(i).getQ());
            }
            Qlinha.put(s, t);
        }
        //inicio do código:
        HashSet<Estado> F = new HashSet<Estado>();
        F.add(estadoInicial);
        HashSet<Estado> I = new HashSet<Estado>();
        HashSet<Estado> G = new HashSet<Estado>();
        G.add(estadoInicial);
        HashSet<Estado> Glinha = new HashSet<Estado>();
        Glinha.add(estadoInicial);
        boolean fIntercepGlinha = false;
        while (fIntercepGlinha == false){
            HashSet<Estado> Scandidato = new HashSet<Estado>();
            Scandidato.addAll(F);//linha 10
            Scandidato.retainAll(Glinha);
            if (Scandidato.iterator().hasNext()) {
                Estado s = Scandidato.iterator().next();
                F.remove(s);//linha 11
                F.addAll(retornaTransicoes(s, I));//linha 12
                I.add(s);//linha 13
                G.addAll((I));//linha 14
                G.addAll((F));
                HashSet<Estado> Z = new HashSet<>();//linha 16
                Z.add(s);//linha 16
                HashSet<Estado> pais = new HashSet<>();
                HashSet<Estado> adicionados = new HashSet<>();
                pais.add(s);
                while (pais.size() != 0) {
                    Estado pai = pais.iterator().next();
                    pais.remove(pai);
                    HashSet<Estado> PaiCandidato = new HashSet<Estado>();
                    if (pai.getEstadoPai() != null) {
                        PaiCandidato.addAll(pai.getEstadoPai());
                        PaiCandidato.retainAll(Glinha);
                        Z.addAll(PaiCandidato);
                        Iterator<Estado> it = PaiCandidato.iterator();
                        while (it.hasNext()) {
                            Estado est = it.next();
                            if (adicionados.contains(est) == false) {
                                pais.addAll(PaiCandidato);
                                adicionados.addAll(PaiCandidato);
                            }
                        }
                    }
                }
                policyIterationLAO(Qlinha,Vlinha, Z);
                Set<Estado> estadoAux = new HashSet<>();
                estadoAux.add(estadoInicial);


                valueIterationRebuild(Qlinha,Vlinha, G);
                Glinha.clear();
                boolean contem = true;

                while (contem == true && !estadoAux.isEmpty()) {
                    Estado aux = estadoAux.iterator().next();
                    estadoAux.remove(aux);
                    List<Estado> e = retornaMelhorTransicoes(aux);
                    if (e.stream().anyMatch(x -> x.isGoal()) == false) {
                        contem = G.containsAll(e);
                        if (contem == true && !Glinha.containsAll(e)) {
                            Glinha.addAll(e);
                            estadoAux.addAll(e);
                        }
                    }

                }
                if (Glinha.contains(p.getInitialState())==false)
                {Glinha.add(p.getInitialState());

                }


                HashSet<Estado> F2 = new HashSet<>();
                F2.addAll(F);
                F2.retainAll(Glinha);
                for (int i = 0; i < p.getFinalStates().size(); i++) {
                    if (F2.contains(p.getFinalState(i)) == true) {
                        fIntercepGlinha = true;
                        break;
                    }

                }
            }
            else {//linha 8

                break;//linha 8
            }
        }
        GlinhaCopia.addAll(Glinha);



    }

    public void executaLaoVI(int dynammicAlgorithm) throws Exception {
        boolean nonTerminalTip = true;
        int exp=0;

        HashMap<Estado, HashMap<Acao, Double>> Qlinha1 = new HashMap<>();//map para guardar o Qanterior.
        for (Estado s : p.getEstados().values()) {

            HashMap<Acao, Double> t = new HashMap<Acao, Double>();
            int tamanho = s.actionSize();
            for (int i = 0; i < tamanho; i++) {

                t.put(s.getAction(i), s.getAction(i).getQ());
            }
            Qlinha1.put(s, t);
        }
        //inicio do código:
        HashSet<Estado> F = new HashSet<Estado>();
        F.add(estadoInicial);
        HashSet<Estado> I = new HashSet<Estado>();
        HashSet<Estado> G = new HashSet<Estado>();
        G.add(estadoInicial);
        HashSet<Estado> Glinha = new HashSet<Estado>();
        Glinha.add(estadoInicial);

        boolean fIntercepGlinha = false;
        while (nonTerminalTip) {

            while (fIntercepGlinha == false) {
                HashSet<Estado> Scandidato = new HashSet<Estado>();
                Scandidato.addAll(F);//linha 10
                Scandidato.retainAll(Glinha);
                if (Scandidato.iterator().hasNext()){
                    Estado s = Scandidato.iterator().next();//linha 10
                    F.remove(s);//linha 11
                    F.addAll(retornaTransicoes(s, I));//linha 12
                    //F.addAll(retornaMelhorTransicoes(s));//linha 12
                    I.add(s);//linha 13
                    G.addAll((I));//linha 14
                    G.addAll((F));//linha 14
                    HashSet<Estado> Z = new HashSet<>();//linha 16
                    Z.add(s);//linha 16
                    HashSet<Estado> pais = new HashSet<>();//linha 16
                    HashSet<Estado> adicionados = new HashSet<>();//linha 16
                    pais.add(s);//linha 16
                    while (pais.size() != 0) {//linha 16
                        Estado pai = pais.iterator().next();//linha 16
                        pais.remove(pai);//linha 16
                        HashSet<Estado> PaiCandidato = new HashSet<Estado>();//linha 16
                        if (pai.getEstadoPai() != null) {//linha 16
                            PaiCandidato.addAll(pai.getEstadoPai());//linha 16
                            PaiCandidato.retainAll(Glinha);//linha 16
                            Z.addAll(PaiCandidato);//linha 16
                            Iterator<Estado> it =PaiCandidato.iterator();//linha 16
                            while (it.hasNext()) {//linha 16
                                Estado est=it.next();//linha 16
                                if (adicionados.contains(est)==false) {//linha 16
                                    pais.addAll(PaiCandidato);//linha 16
                                    adicionados.addAll(PaiCandidato);//linha 16
                                }//linha 16
                            }//linha 16
                        }//linha 16
                    }//linha 16
                    //policyIterationLAO(Vlinha,Z);
                    exp=exp+1;
                    valueIteration22(Qlinha1,Z);//linha 17
                    Set<Estado> estadoAux=new HashSet<>();//linha 18
                    estadoAux.add(estadoInicial);//linha 18
                    valueIteration222(Qlinha1,Glinha);

                   /* Iterator<Estado> carrosAsIterator2 = Glinha.iterator();
                    while (carrosAsIterator2.hasNext()) {
                        Estado it = carrosAsIterator2.next();

                        System.out.println( "Glinha2 FIM2 " +it.getName());
                    }
                    System.out.println( );*/

                    Glinha.clear();//linha 18
                    boolean contem =true;//linha 18



                    while (contem ==true && !estadoAux.isEmpty() ) {//linha 18
                        Estado aux = estadoAux.iterator().next();//linha 18
                        estadoAux.remove(aux);//linha 18
                        List<Estado> e = retornaMelhorTransicoes(aux);//linha 18
                        if (e.stream().anyMatch(x -> x.isGoal())==false) {//linha 18
                            contem = G.containsAll(e);//linha 18
                            if (contem == true && !Glinha.containsAll(e)) {//linha 18
                                Glinha.addAll(e);//linha 18
                                estadoAux.addAll(e);//linha 18
                            }
                        }

                    }//linha 18
                    if (Glinha.contains(p.getInitialState())==false)
                    {Glinha.add(p.getInitialState());

                    }


                    HashSet<Estado>F2 = new HashSet<>();//linha 8 dentro do while
                    F2.addAll(F);//linha 8
                    F2.retainAll(Glinha);//linha 8
                    for (int i = 0; i < p.getFinalStates().size(); i++) {//linha 8
                        if (F2.contains(p.getFinalState(i)) == true) {//linha 8
                            fIntercepGlinha = true;//linha 8
                            break;//linha 8
                        }//linha 8

                    }//linha 8
                } else {//linha 8
                    break;//linha 8
                }
              /*  Iterator<Estado> carrosAsIterator = Glinha.iterator();
                while (carrosAsIterator.hasNext()) {
                    Estado it = carrosAsIterator.next();

                    System.out.println( "Glinha FIM " +it.getName());
                }
                System.out.println( );*/
            }


            nonTerminalTip =valueIteration1(Qlinha1,Glinha);
            Glinha.clear();
            boolean contem =true;
            Set<Estado> estadoAux=new HashSet<>();
            estadoAux.add(estadoInicial);
            while (contem ==true && !estadoAux.isEmpty() ) {
                Estado aux = estadoAux.iterator().next();
                estadoAux.remove(aux);
                List<Estado> e = retornaMelhorTransicoes(aux);
                if (e.stream().anyMatch(x -> x.isGoal())==false) {
                    contem = G.containsAll(e);
                    if (contem == true && !Glinha.containsAll(e)) {
                        Glinha.addAll(e);
                        estadoAux.addAll(e);
                    }
                }

            }

        }
        GlinhaCopia.addAll(Glinha);
        System.out.println(exp);
    }

    public List<Estado> retornaMelhorTransicoes(Estado e) {
        List<Estado> a = new ArrayList<Estado>();

        if (e.getBestAction() == -1) {
            int size = e.getAction(0).getTransitions().size();
            for (int i = 0; i < size; i++) {

                a.add(e.getAction(0).getTransitions().get(i).getState());
            }
        } else {
            int size = e.getAction(e.getBestAction()).getTransitions().size();
            for (int i = 0; i < size; i++) {
Estado d=e.getAction(e.getBestAction()).getTransitions().get(i).getState();

if ( d.getEstadoPai().contains(e)==false)
{
    d.setEstadoPai(e);
}
                a.add(d);

            }
        }
        return a;
    }

    public List<Estado> retornaTransicoes(Estado e, HashSet<Estado> I) {
        List<Estado> a = new ArrayList<Estado>();

        int size2 = e.actionSize();
        for (int j = 0; j < size2; j++) {
            int size = e.getAction(j).getTransitions().size();

            for (int i = 0; i < size; i++) {
                Estado compara = e.getAction(j).getTransitions().get(i).getState();
                if(compara!=e)
                {
                    compara.setEstadoPai(e);
                   // System.out.println( "filho " + compara.getName() + "pai "+e.getName());

                if (I.contains(compara) == false) {

                    a.add(compara);

                }
                }
            }
        }


        return a;
    }






   /* public void executa(int dynammicAlgorithm) {
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
              //  expande(estadoInicial,Qlinha1);

                nonTerminalTip = depthFirstSearch(estadoInicial, Qlinha1);
            } while (nonTerminalTip);
            this.pprint(0, 0);
            if (dynammicAlgorithm == VALUE) {
                convergido++;
                nonTerminalTip = valueIteration(Qlinha1);
            }
            this.pprint(0, 0);
        }
        this.pprint(0, 0);
    }

    public void exec_IV() {
*
    }*/

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
                a[x - 1][y - 1] = String.valueOf(e.getCost());
            } //else if (e.wasExpanded()) System.out.println("Estado: " + e.getName() + " acao: " + p.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            else {
                if (e.getAction(0) != null & e.getAction(1) != null && e.getAction(2) != null && e.getAction(3) != null) {
                    //System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " +e.getAction(0).getName() +" "+  e.getAction(0).getQ() + "   " + e.getAction(1).getName() +" "+ e.getAction(1).getQ() + "  " + e.getAction(2).getName() +" "+e.getAction(2).getQ() + "   " +e.getAction(3).getName() +" "+ e.getAction(3).getQ());}
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getName() + " " + e.getCost());
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
                a[x - 1][y - 1] = e.getAction(e.getBestAction()).getName();
                file.escreveArquivo("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());

            }


        }
        for (int i = 0; i < a1; i++) {
            {
                for (int i2 = 0; i2 < b1; i2++) {
                    if (a[i2][i] == null) {
                        System.out.print("0");
                        file.escreveArquivo2("0");
                    } else {
                        if (a[i2][i].equals("move-N")) {
                            System.out.print("↑");
                            file.escreveArquivo2("↑");
                        } else if (a[i2][i].equals("move-S")) {
                            System.out.print("↓");
                            file.escreveArquivo2("↓");
                        } else if (a[i2][i].equals("move-E")) {
                            System.out.print("→");
                            file.escreveArquivo2("→");
                        } else if (a[i2][i].equals("move-W")) {
                            System.out.print("←");
                            file.escreveArquivo2("←");
                        }
                    }
                    // System.out.print(a[i2][i] + "    ");

                }
                System.out.println();
                file.escreveArquivo("");
            }
        }
        System.out.println("\nQuantidade de estados na politica: " + setZ.size());
        file.escreveArquivo("Quantidade de estados na politica: " + setZ.size());
        System.out.println("Quantidade de estados expandidos: " + count);
        file.escreveArquivo("Quantidade de estados expandidos: " + count);
        //file.fechaArquivo();
    }
    public void printLAO(Arquivo file, int a1, int b1) {

        String[][] a = new String[b1][a1];
        Iterator<Estado> it= GlinhaCopia.iterator();
        while (it.hasNext()) {

            Estado e = it.next();


            if (e.isGoal()) {
                System.out.println("Estado: " + e.getName() + " custo: " + e.getCost());
                String d = e.getName();
                int x = e.getX();
                int y = e.getY();
                a[x - 1][y - 1] = String.valueOf(e.getCost());
            } //else if (e.wasExpanded()) System.out.println("Estado: " + e.getName() + " acao: " + p.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());
            else {
                if (e.getAction(0) != null & e.getAction(1) != null && e.getAction(2) != null && e.getAction(3) != null) {
                    //System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " +e.getAction(0).getName() +" "+  e.getAction(0).getQ() + "   " + e.getAction(1).getName() +" "+ e.getAction(1).getQ() + "  " + e.getAction(2).getName() +" "+e.getAction(2).getQ() + "   " +e.getAction(3).getName() +" "+ e.getAction(3).getQ());}
                    System.out.println("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getAction(0).getName() + " " + e.getCost());
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
                a[x - 1][y - 1] = e.getAction(e.getBestAction()).getName();
                file.escreveArquivo("Estado: " + e.getName() + " acao: " + e.getAction(e.getBestAction()).getName() + " custo: " + e.getCost());

            }


        }
        for (int i = 0; i < a1; i++) {
            {
                for (int i2 = 0; i2 < b1; i2++) {
                    if (a[i2][i] == null) {
                        System.out.print("0");
                        file.escreveArquivo2("0");
                    } else {
                        if (a[i2][i].equals("move-N")) {
                            System.out.print("↑");
                            file.escreveArquivo2("↑");
                        } else if (a[i2][i].equals("move-S")) {
                            System.out.print("↓");
                            file.escreveArquivo2("↓");
                        } else if (a[i2][i].equals("move-E")) {
                            System.out.print("→");
                            file.escreveArquivo2("→");
                        } else if (a[i2][i].equals("move-W")) {
                            System.out.print("←");
                            file.escreveArquivo2("←");
                        }
                    }
                    // System.out.print(a[i2][i] + "    ");

                }
                System.out.println();
                file.escreveArquivo("");
            }
        }
        System.out.println("\nQuantidade de estados na politica: " + GlinhaCopia.size());
        file.escreveArquivo("Quantidade de estados na politica: " + GlinhaCopia.size());
        System.out.println("Quantidade de estados expandidos: " + count);
        file.escreveArquivo("Quantidade de estados expandidos: " + count);
        //file.fechaArquivo();
    }
    public void printDenis(Arquivo file,int a1,int b1) {
        Iterator<Estado> it= GlinhaCopia.iterator();
        while (it.hasNext()) {

            Estado e = it.next();
            System.out.println("nome "+ e.getName() +" custo "+ e.getCost());
        }

        System.out.println("\nQuantidade de estados na politica: " + GlinhaCopia.size());
        file.escreveArquivo("Quantidade de estados na politica: " + GlinhaCopia.size());
        System.out.println("Quantidade de estados expandidos: " + count);
        file.escreveArquivo("Quantidade de estados expandidos: " + count);
        file.fechaArquivo();
    }

    public void printSIRT(Arquivo file, int a1, int b1) {
        Set<Integer> chaves = setZ.keySet();
        String[][] a = new String[b1][a1];

        for (Integer chave : chaves) {

            Estado e = vertices.get(chave);


            if (e.isGoal()) {
                System.out.println("Estado: " + e.getName() + " custo: " + e.getCost());
                String d = e.getName();
                int x = e.getX();
                int y = e.getY();
                a[x - 1][y - 1] = String.valueOf(e.getCost());
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
                a[x - 1][y - 1] = e.getAction(e.getBestAction()).getName();
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
    private boolean depthFirstSearch(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) throws Exception {
        boolean expanded = false;
        if (estadoCorrente.wasExpanded()) {
            boolean firstExpanded = false;
            if (estadoCorrente.getBestAction() != -1) {
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
            }
        } else {
            expande(estadoCorrente, Qlinha1);
            return true;
        }

        // minUtility(estadoCorrente, Qlinha1);
        this.pprint(0, 0);

        return expanded;
    }

    private boolean depthFirstSearchPI(Estado estadoCorrente, HashMap<Estado, Double> Vlinha) throws Exception {
        boolean expanded = false;
        if (estadoCorrente.wasExpanded()) {
            boolean firstExpanded = false;
            if (estadoCorrente.getBestAction() != -1) {
                Acao a = estadoCorrente.getAction(estadoCorrente.getBestAction());
                LinkedList<Transicao> t = a.getTransitions();
                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {//nao pra todos
                    Transicao trans = it.next();
                    Estado e = trans.getState();

                    if (!setT.containsKey(e.hashCode()) && !e.isGoal()) {
                        setT.put(e.hashCode(), true);
                        firstExpanded = depthFirstSearchPI(e, Vlinha);
                        if (firstExpanded) {
                            expanded = firstExpanded;
                        }
                    }
                }
            }
        } else {
            expandePI(estadoCorrente, Vlinha);
            return true;
        }

        // minUtility(estadoCorrente, Qlinha1);
        this.pprint(0, 0);

        return expanded;
    }

    private void expande(Estado estadoExp, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) throws Exception {
        count++;
        //System.out.println("\nExpande: " + estadoExp.getName());

        int size = estadoExp.actionSize();
        for (int i = 0; i < size; i++) {
            Acao a = estadoExp.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                Transicao trans = it.next();
                Estado e = trans.getState();

                if (!vertices.containsKey(e.hashCode())) {
                    vertices.put(e.hashCode(), e);

                    h.calcula(e);
                    HashMap<Acao, Double> m = new HashMap<Acao, Double>();
                    int tamanho = e.actionSize();
                    for (int n = 0; n < tamanho; n++) {//itera por todas acoes s
                        Acao ay = e.getAction(n);//pega ação
                        m.put(ay, ay.getQ());
                    }

                    Qlinha1.put(e, m);


                }
            }
        }
        estadoExp.setExpanded();
        // minUtility(estadoExp, Qlinha1);
        valueIteration2(Qlinha1);
        this.pprint(0, 0);
    }

    private void expandePI(Estado estadoExp, HashMap<Estado, Double> Vlinha) throws Exception {
        count++;
        //System.out.println("\nExpande: " + estadoExp.getName());

        int size = estadoExp.actionSize();
        for (int i = 0; i < size; i++) {
            Acao a = estadoExp.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                Transicao trans = it.next();
                Estado e = trans.getState();

                if (!vertices.containsKey(e.hashCode())) {
                    vertices.put(e.hashCode(), e);

                    h.calcula(e);
                    HashMap<Acao, Double> m = new HashMap<Acao, Double>();
                    int tamanho = e.actionSize();
                    double valor = 0;
                    for (int n = 1; n < tamanho; n++) {//itera por todas acoes s
                        Acao ay = e.getAction(n);//pega ação
                        valor = ay.getQ();
                    }

                    Vlinha.put(e, valor);


                }
            }
        }
        estadoExp.setExpanded();
        // minUtility(estadoExp, Qlinha1);
        policyIterationILAO2(Vlinha);
        this.pprint(0, 0);
    }

    private boolean valueIteration(HashMap<Estado, HashMap<Acao, Double>> Qlinha1) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;

        double error;
        LinkedList<Estado> visited = new LinkedList<Estado>();


        setZ.clear();
        visited.add(estadoInicial);
        setZ.put(estadoInicial.hashCode(), true);
        // error = 0;


        // Busca em profundidade

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.add(estadoInicial);
        while (!visited.isEmpty()) {

            Estado s = visited.remove();
            if (s.getBestAction() != -1) {
                //    double errorI = update(s, Qlinha1);

                Acao a = s.getAction(s.getBestAction());
                List<Transicao> t = a.getTransitions();

                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                    Transicao trans = it.next();
                    Estado e = trans.getState();

                    if (!setZ.containsKey(e.hashCode()) && !e.isGoal() && e.wasExpanded() == true) {
                        setZ.put(e.hashCode(), true);
                        visited.add(e);
                        visited2.add(e);
                    }
                }
            }
        }

        do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                int tttt = s.getBestAction();
                double errorI = update(s, Qlinha1);
                error = Math.max(errorI, error);
                int tttt2 = s.getBestAction();
                if (tttt != -1 && tttt != tttt2) {
                    //if(tttt !=tttt2) {
                    return true;
                    //valueIteration(Qlinha1);
                    //throw new Exception();


                    //  a = true;
                }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            }
            qtdeILAO++;


        } while (error > epsilon);


        return false;
    }
    private boolean valueIteration1(HashMap<Estado, HashMap<Acao, Double>> Qlinha1,HashSet<Estado> Glinha) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;
        //double epsilon = 0.1;
        double error;
        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.addAll(Glinha);

        do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                int tttt = s.getBestAction();
                double errorI = update(s, Qlinha1);
                error = Math.max(errorI, error);
                int tttt2 = s.getBestAction();
                if (tttt != -1 && tttt != tttt2) {
                    //if(tttt !=tttt2) {
                    return true;
                    //valueIteration(Qlinha1);
                    //throw new Exception();


                    //  a = true;
                }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            }
            qtdeILAO++;


        } while (error > epsilon);


        return false;
    }

    private boolean valueIteration2(HashMap<Estado, HashMap<Acao, Double>> Qlinha1) throws Exception {
        //double epsilon = Math.pow(10, -16);
        //double epsilon = 0.0000001;
        double epsilon=0.1;
        double error;
        LinkedList<Estado> visited = new LinkedList<Estado>();


        setZ.clear();
        visited.add(estadoInicial);
        setZ.put(estadoInicial.hashCode(), true);
        // error = 0;


        // Busca em profundidade

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.add(estadoInicial);
        while (!visited.isEmpty()) {

            Estado s = visited.remove();
            if (s.getBestAction() != -1) {
                //    double errorI = update(s, Qlinha1);

                Acao a = s.getAction(s.getBestAction());
                List<Transicao> t = a.getTransitions();

                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                    Transicao trans = it.next();
                    Estado e = trans.getState();

                    if (!setZ.containsKey(e.hashCode()) && !e.isGoal() && e.wasExpanded() == true) {
                        setZ.put(e.hashCode(), true);
                        visited.add(e);
                        visited2.add(e);
                    }
                }
            }
        }

        do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                //int tttt=s.getBestAction();
                double errorI = update(s, Qlinha1);
                error = Math.max(errorI, error);
                // int tttt2=s.getBestAction();
                // if(tttt!=-1 && tttt !=tttt2) {
                //if(tttt !=tttt2) {
                //return true;
                //valueIteration(Qlinha1);
                //   throw new Exception();

                //  a = true;
            }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            qtdeILAO++;


        } while (error > epsilon);


        return false;
    }
    private boolean valueIterationRebuild(HashMap<Estado, HashMap<Acao, Double>> Qlinha,HashMap<Estado, Double> Vlinha, Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;
        //double epsilon=0.1;
        double error;
        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.addAll(Z);
        //do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                //int tttt=s.getBestAction();
                double errorI = updateforRebuild(s, Qlinha,Vlinha);
               // error = Math.max(errorI, error);
                // int tttt2=s.getBestAction();
                // if(tttt!=-1 && tttt !=tttt2) {
                //if(tttt !=tttt2) {
                //return true;
                //valueIteration(Qlinha1);
                //   throw new Exception();

                //  a = true;
            }
        for (int i = 0; i < visited2.size(); i++) {
            Estado s = visited2.get(i);
            Vlinha.put(s, s.getCost());
        }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            qtdeILAO++;


      //  } while (error > epsilon);


        return false;
    }
    private boolean valueIteration22(HashMap<Estado, HashMap<Acao, Double>> Qlinha1,Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        //  double epsilon = 0.0000001;
        double epsilon = 0.0000000001;
        double error;

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.addAll(Z);

        do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                //int tttt=s.getBestAction();
                double errorI = update(s, Qlinha1);
                error = Math.max(errorI, error);
                // int tttt2=s.getBestAction();
                // if(tttt!=-1 && tttt !=tttt2) {
                //if(tttt !=tttt2) {
                //return true;
                //valueIteration(Qlinha1);
                //   throw new Exception();

                //  a = true;
            }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            qtdeILAO++;


        } while (error > epsilon);


        return false;
    }

    private boolean valueIteration23(HashMap<Estado, HashMap<Acao, Double>> Qlinha1,Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        //  double epsilon = 0.0000001;
        double epsilon = 0.1;
        double error;

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.addAll(Z);

        do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                //int tttt=s.getBestAction();
                double errorI = update(s, Qlinha1);
                error = Math.max(errorI, error);
                // int tttt2=s.getBestAction();
                // if(tttt!=-1 && tttt !=tttt2) {
                //if(tttt !=tttt2) {
                //return true;
                //valueIteration(Qlinha1);
                //   throw new Exception();

                //  a = true;
            }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            qtdeILAO++;


        } while (error > epsilon);


        return false;
    }


    private boolean valueIteration222(HashMap<Estado, HashMap<Acao, Double>> Qlinha1,Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        //  double epsilon = 0.0000001;
        double epsilon = 0.01;
        double error;

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.addAll(Z);

        // do {
        // System.out.println("visitar");
        error = 0;
        for (int i = 0; i < visited2.size(); i++) {
            boolean a = false;
            Estado s = visited2.get(i);
            // System.out.println(s.getName());
            //int tttt=s.getBestAction();
            double errorI = update(s, Qlinha1);
            error = Math.max(errorI, error);
            // int tttt2=s.getBestAction();
            // if(tttt!=-1 && tttt !=tttt2) {
            //if(tttt !=tttt2) {
            //return true;
            //valueIteration(Qlinha1);
            //   throw new Exception();

            //  a = true;
        }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
        qtdeILAO++;


        // } while (error > epsilon);


        return false;
    }
    private boolean valueIteration2222(HashMap<Estado, HashMap<Acao, Double>> Qlinha1,Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        //  double epsilon = 0.0000001;
        double epsilon = 0.01;
        double error;

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.addAll(Z);

        // do {
        // System.out.println("visitar");
        error = 0;
        for (int i = 0; i < visited2.size(); i++) {
            boolean a = false;
            Estado s = visited2.get(i);
            // System.out.println(s.getName());
            //int tttt=s.getBestAction();
            double errorI = updatev22(s, Qlinha1);
            error = Math.max(errorI, error);
            // int tttt2=s.getBestAction();
            // if(tttt!=-1 && tttt !=tttt2) {
            //if(tttt !=tttt2) {
            //return true;
            //valueIteration(Qlinha1);
            //   throw new Exception();

            //  a = true;
        }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
        qtdeILAO++;


        // } while (error > epsilon);


        return false;
    }


    private boolean policyIterationILAO(HashMap<Estado, Double> Vlinha) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;

        double error;
        LinkedList<Estado> visited = new LinkedList<Estado>();


        setZ.clear();
        visited.add(estadoInicial);
        setZ.put(estadoInicial.hashCode(), true);
        // error = 0;


        // Busca em profundidade

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.add(estadoInicial);
        while (!visited.isEmpty()) {

            Estado s = visited.remove();
            if (s.getBestAction() != -1) {
                //    double errorI = update(s, Qlinha1);

                Acao a = s.getAction(s.getBestAction());
                List<Transicao> t = a.getTransitions();

                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                    Transicao trans = it.next();
                    Estado e = trans.getState();

                    if (!setZ.containsKey(e.hashCode()) && !e.isGoal() && e.wasExpanded() == true) {
                        setZ.put(e.hashCode(), true);
                        visited.add(e);
                        visited2.add(e);
                    }
                }
            }
        }

        do {
            // System.out.println("visitar");
            error = 0;
            for (int i = 0; i < visited2.size(); i++) {
                boolean a = false;
                Estado s = visited2.get(i);
                // System.out.println(s.getName());
                int tttt = s.getBestAction();
                double errorI = updateLAOversaoAantiga(s, Vlinha);
                error = Math.max(errorI, error);
                int tttt2 = s.getBestAction();
                if (tttt != -1 && tttt != tttt2) {
                    //if(tttt !=tttt2) {
                    return true;
                    //valueIteration(Qlinha1);
                    //throw new Exception();


                    //  a = true;
                }
            /*   if (i==visited2.size()-1&&a==true) {
                 //  valueIteration(Qlinha1);
               }*/
            }
            qtdeILAO++;


        } while (error > epsilon);


        return false;
    }

    private boolean policyIterationILAO2(HashMap<Estado, Double> Vlinha) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;

        double error;
        LinkedList<Estado> visited = new LinkedList<Estado>();


        setZ.clear();
        visited.add(estadoInicial);
        setZ.put(estadoInicial.hashCode(), true);
        // error = 0;


        // Busca em profundidade

        LinkedList<Estado> visited2 = new LinkedList<Estado>();
        visited2.add(estadoInicial);
        while (!visited.isEmpty()) {

            Estado s = visited.remove();
            if (s.getBestAction() != -1) {
                //    double errorI = update(s, Qlinha1);

                Acao a = s.getAction(s.getBestAction());
                List<Transicao> t = a.getTransitions();

                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) {
                    Transicao trans = it.next();
                    Estado e = trans.getState();

                    if (!setZ.containsKey(e.hashCode()) && !e.isGoal() && e.wasExpanded() == true) {
                        setZ.put(e.hashCode(), true);
                        visited.add(e);
                        visited2.add(e);
                    }
                }
            }
        }
///////////////igual ValueIteration pois cria o grupo
        boolean naomudouPolitica = true;
        do {
            naomudouPolitica = true;
            int qw = 0;
            do {//policy evaluation
                // System.out.println("visitar");
                error = 0;

                for (int i = 0; i < visited2.size(); i++) {

                    boolean a = false;
                    Estado s = visited2.get(i);
                    double errorI = updateLAOversaoAantiga(s, Vlinha);
                    error = Math.max(errorI, error);
                }

                qtdeILAO++;
                for (int i = 0; i < visited2.size(); i++) {
                    Estado s = visited2.get(i);
                    Vlinha.put(s, s.getCost());
                }

            } while (error > epsilon);

            boolean f = true;
            for (int i = 0; i < visited2.size(); i++) {//policy improvement


                Estado s = visited2.get(i);

                naomudouPolitica = updateLAO2(s, Vlinha);
                if (naomudouPolitica == false) {
                    f = false;
                }

            }
            naomudouPolitica = f;
        } while (naomudouPolitica == false);

        return false;
    }

    private boolean policyIterationLAO(HashMap<Estado, HashMap<Acao, Double>> Qlinha,HashMap<Estado, Double> Vlinha, Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;
        double error;

        LinkedList<Estado> visited = new LinkedList<Estado>();
        visited.addAll(Z);


///////////////igual ValueIteration pois criar o grupo
        boolean naomudouPolitica = true;
        //do {
          //  naomudouPolitica = true;
            //   int qw = 0;
            int conta = 0;
            do {//policy evaluation
                // System.out.println("visitar");
                error = 0;

                boolean[] mudou = new boolean[1];
                for (int i = 0; i < visited.size(); i++) {


                    Estado s = visited.get(i);
                    double errorI = updateLAO(s, Vlinha, Qlinha, mudou);
                    error = Math.max(errorI, error);
                    System.out.println(errorI);
                 //   if (mudou[0] == true) {
                  //      break;
                  //  }
                }
                if (mudou[0] == true) {
                    break;
                }
                qtdeILAO++;
                for (int i = 0; i < visited.size(); i++) {
                    Estado s = visited.get(i);
                    Vlinha.put(s, s.getCost());
                }
//conta =conta+1;
//if (conta > 10000)
/*
{
    break;
}*/

          /*  if (mudou[0] == true) {
                           break;
                       }*/
            } while (error > epsilon);

          //  boolean f = true;
        /*    for (int i = 0; i < visited.size(); i++) {//policy improvement


                Estado s = visited.get(i);

                naomudouPolitica = updateLAO2(s, Vlinha);
                //naomudouPolitica = updateLAO4(s, Qlinha);
                if (naomudouPolitica == false) {
                    f = false;
                }

            }
            naomudouPolitica = f;
        } while (naomudouPolitica == false);*/

            return false;
        }

    private boolean policyIterationLAOVQlinha(HashMap<Estado, Double> Vlinha, Set<Estado>Z) throws Exception {
        //double epsilon = Math.pow(10, -16);
        double epsilon = 0.0000001;
        double error;

        LinkedList<Estado> visited = new LinkedList<Estado>();
        visited.addAll(Z);


///////////////igual ValueIteration pois cria o grupo
        boolean naomudouPolitica = true;
        //  do {
        //   naomudouPolitica = true;
        int qw = 0;
        do {//policy evaluation
            // System.out.println("visitar");
            error = 0;

            for (int i = 0; i < visited.size(); i++) {


                Estado s = visited.get(i);
                double errorI = updateLAOversaoAantiga(s, Vlinha);
                error = Math.max(errorI, error);
                System.out.println(errorI);
            }

            qtdeILAO++;
            for (int i = 0; i < visited.size(); i++) {
                Estado s = visited.get(i);
                Vlinha.put(s, s.getCost());
            }

        } while (error > epsilon);

        boolean f = true;
        for (int i = 0; i < visited.size(); i++) {//policy improvement


            Estado s = visited.get(i);

            naomudouPolitica = updateLAO2(s, Vlinha);
            //  if (naomudouPolitica == false) {
            //      f = false;
        }

        // }
        //   naomudouPolitica = f;
        // } while (naomudouPolitica == false);

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
    private double updatev22(Estado s, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        double oldCost = s.getCost();
        minUtilityForRebuildv22(s, Qlinha1);
        double newCost = s.getCost();
        double errorI = Math.abs(oldCost - newCost);

        return errorI;
    }
    private double updateforRebuild(Estado s, HashMap<Estado, HashMap<Acao, Double>> Qlinha1,HashMap<Estado, Double> Vlinha) {
        double oldCost = s.getCost();
        //minUtilityForRebuild(s, Qlinha1,Vlinha);
        minUtilityForRebuildv2(s, Qlinha1,Vlinha);
        double newCost = s.getCost();
        double errorI = Math.abs(oldCost - newCost);

        return errorI;
    }

    private double updateLAO(Estado s, HashMap<Estado, Double> Vlinha, HashMap<Estado, HashMap<Acao, Double>> Qlinha,boolean []mudou ) {
        double oldCost = s.getCost();
         mudou[0]=minUtilityLAO(s, Vlinha,Qlinha);
        double newCost = s.getCost();
        double errorI = Math.abs(oldCost - newCost);

        return errorI;
    }
    private double updateLAOversaoAantiga(Estado s, HashMap<Estado, Double> Vlinha) {
        double oldCost = s.getCost();
        minUtilityLAOvAntiga(s, Vlinha);
        double newCost = s.getCost();
        double errorI = Math.abs(oldCost - newCost);

        return errorI;
    }



    private boolean updateLAO2(Estado s, HashMap<Estado, Double> Vlinha) {
        //double oldCost = s.getCost();
        boolean mudou;
        mudou = minUtilityLAO2(s, Vlinha);
        //double newCost = s.getCost();
        // double errorI = Math.abs(oldCost - newCost);

        return mudou;
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
        //  double Qmax = Double.NEGATIVE_INFINITY;
        double Qmax = Double.POSITIVE_INFINITY;
        int size = estadoCorrente.actionSize();
        HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            somatudo = 0;

            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições estado s
            {
                Transicao trans = it.next();
                int tamanho = trans.getState().actionSize();
                double qcomparadorQlinha = Double.POSITIVE_INFINITY;
               // qcomparadorQlinha = trans.getState().isGoal() == true ? qmeta : Double.POSITIVE_INFINITY;// auxilia na verificação do max Q(s",a) ou retorna o valor da heuristica.

                for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.

                    Acao a2 = trans.getState().getAction(i2);
                    double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                    //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                    if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
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
                qtdeILAO2++;
                somatudo += prob * X;
            }
            somatudo = somatudo * p.getTamanhoPasso();
            double calculaQsa = Q + somatudo;
            // if (Qmax < calculaQsa) {
            // if (Qmax > calculaQsa) {//pega o Qmax
            //  if(Double.compare(Qmax,calculaQsa)<0)//pega max
            if (Double.compare(Qmax, calculaQsa) > 0)//pega min
            {
                Qmax = calculaQsa;
                bestAction = i;
            }
            guardaQantesAtualizar.put(a, calculaQsa); //guarda todos os Q para atualizar no fim.

        }

        HashMap<Acao, Double> t = new HashMap<Acao, Double>();
        // System.out.println(estadoCorrente.getName());
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
        estadoCorrente.setBestEdge(bestAction);
    }
    private void minUtilityForRebuildv22(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1) {
        int bestAction = -1;
        double qlinha = 0;
        double Q = 0;
        double somatudo = 0;
        //  double Qmax = Double.NEGATIVE_INFINITY;
        double Qmax = Double.POSITIVE_INFINITY;
        int size = estadoCorrente.actionSize();
        HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            somatudo = 0;

            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições estado s
            {
                Transicao trans = it.next();
                int tamanho = trans.getState().actionSize();
                double qcomparadorQlinha = Double.NEGATIVE_INFINITY;
                qcomparadorQlinha = trans.getState().isGoal() == true ? qmeta : Double.POSITIVE_INFINITY;// auxilia na verificação do max Q(s",a) ou retorna o valor da heuristica.

                for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.

                    Acao a2 = trans.getState().getAction(i2);
                    double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                    //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                    if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
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
                qtdeILAO2++;
                somatudo += prob * X;
            }
            somatudo = somatudo * p.getTamanhoPasso();
            double calculaQsa = Q + somatudo;
            // if (Qmax < calculaQsa) {
            // if (Qmax > calculaQsa) {//pega o Qmax
            //  if(Double.compare(Qmax,calculaQsa)<0)//pega max
            if (Double.compare(Qmax, calculaQsa) > 0)//pega min
            {
                Qmax = calculaQsa;
                bestAction = i;
            }
            guardaQantesAtualizar.put(a, calculaQsa); //guarda todos os Q para atualizar no fim.

        }

        HashMap<Acao, Double> t = new HashMap<Acao, Double>();
        // System.out.println(estadoCorrente.getName());
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
        estadoCorrente.setBestEdge(bestAction);
    }
    private void minUtilityForRebuild(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1,HashMap<Estado, Double> Vlinha) {
        int bestAction = -1;
        double qlinha = 0;
        double Q = 0;
        double somatudo = 0;
        //  double Qmax = Double.NEGATIVE_INFINITY;
        double Qmax = Double.POSITIVE_INFINITY;
        int size = estadoCorrente.actionSize();
        HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            somatudo = 0;

            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições estado s
            {
                Transicao trans = it.next();
                int tamanho = trans.getState().actionSize();
                double qcomparadorQlinha = Double.NEGATIVE_INFINITY;
                qcomparadorQlinha = trans.getState().isGoal() == true ? qmeta : Double.POSITIVE_INFINITY;// auxilia na verificação do max Q(s",a) ou retorna o valor da heuristica.

                for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.

                    Acao a2 = trans.getState().getAction(i2);
                    double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                    //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                    if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
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
                qtdeILAO2++;
                somatudo += prob * X;
            }
            somatudo = somatudo * p.getTamanhoPasso();
            double calculaQsa = Q + somatudo;
            // if (Qmax < calculaQsa) {
            // if (Qmax > calculaQsa) {//pega o Qmax
            //  if(Double.compare(Qmax,calculaQsa)<0)//pega max
            if (Double.compare(Qmax, calculaQsa) > 0)//pega min
            {
                Qmax = calculaQsa;
                bestAction = i;
            }
            guardaQantesAtualizar.put(a, calculaQsa); //guarda todos os Q para atualizar no fim.

        }

        HashMap<Acao, Double> t = new HashMap<Acao, Double>();
        // System.out.println(estadoCorrente.getName());
        for (Map.Entry<Acao, Double> entry : guardaQantesAtualizar.entrySet()) {//atualiza Qs do estado corrente
            Acao key = entry.getKey();
            Double value = entry.getValue();
            key.setQ(value);
            t.put(key, value);
            // System.out.print(key.getName()+":"+value);
        }
        // System.out.println();
        Qlinha1.put(estadoCorrente, t);
        Vlinha.put(estadoCorrente,Qmax);
        estadoCorrente.setCost(Qmax);//V
        estadoCorrente.setBestEdge(bestAction);
    }

    private void minUtilityForRebuildv2(Estado estadoCorrente, HashMap<Estado, HashMap<Acao, Double>> Qlinha1,HashMap<Estado, Double> Vlinha) {


            double somatudo = 0;
            int size = estadoCorrente.actionSize();
            HashMap<Acao, Double> guardador = new HashMap<Acao, Double>();
            double x = Double.MAX_VALUE;
            int melhorAção = estadoCorrente.getBestAction();
            //int melhorAçãoaux = estadoCorrente.getBestAction();
            if (melhorAção == -1) {
                melhorAção = 0;
            }
            for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                Acao a = estadoCorrente.getAction(i);
                LinkedList<Transicao> t = a.getTransitions();
                somatudo = 0;
                for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da politica a
                {
                    Transicao trans = it.next();
                    double prob = trans.getProbA();
                    double Vtransicao = Vlinha.get(trans.getState());
                    double Vanterior = Vlinha.get(estadoCorrente);//qanterior
                    double X = calculaX(a.getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
                    qtdeILAO2++;
                    somatudo += prob * X;
                }
                somatudo = somatudo * p.getTamanhoPasso();
                double calculaQsa = Vlinha.get(estadoCorrente) + somatudo;
                guardador.put(a, calculaQsa);
                //   Vlinha.put(estadoCorrente, calculaQsa);
                //   estadoCorrente.setCost(calculaQsa);//V
            }
            Qlinha1.put(estadoCorrente,guardador);
            Acao guardamelhor = null;
            int melhorestado = 0;

            for (int o = 0; o < size; o++) {
                Acao h = estadoCorrente.getAction(o);
                if (x > guardador.get(h)) {
                    x = guardador.get(h);
                    guardamelhor = h;
                    melhorestado = o;
                }
            }
        estadoCorrente.setCost(x);//V
           // Vlinha.put(estadoCorrente,x);
            estadoCorrente.setBestEdge(melhorestado);



        }


    private void minUtilityLAOBelman(Estado estadoCorrente, HashMap<Estado, Double> Vlinha) {
        //int bestAction = -1;
        //  double Q = 0;
        double somatudo = 0;
        somatudo = 0;
        int melhorAção = estadoCorrente.getBestAction();
        if (melhorAção == -1) {
            melhorAção = 0;
        }

        for (Iterator<Transicao> it = estadoCorrente.getAction(melhorAção).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
        {
            Transicao trans = it.next();
            double prob = trans.getProbA();
            // double Vtransicao = Vlinha.get(trans.getState());
            double Vtransicao = trans.getState().getCost();
            //double Vanterior = Vlinha.get(estadoCorrente);//qanterior
            // somatudo+=(prob*(estadoCorrente.getAction(melhorAção).getCost()+(p.getDiscount()* Vtransicao)));
            somatudo = somatudo + prob * (estadoCorrente.getAction(melhorAção).getCost() + p.getDiscount() * Vtransicao);
        }

        // V[s] += t * (r + gamma * prev_V[s_next])
        // double calculaQsa = estadoCorrente.getCost() + somatudo;
        // somatudo+=(estadoCorrente.getAction(melhorAção).getCost());
        // somatudo=somatudo+estadoCorrente.getCost();
        estadoCorrente.setCost(somatudo);//V
        // Vlinha.put(estadoCorrente,somatudo);


    }

    private boolean minUtilityLAOBelman2(Estado estadoCorrente, HashMap<Estado, Double> Vlinha) {
        boolean naomudouPolitica = true;
        double somatudo = 0;
        int size = estadoCorrente.actionSize();
        HashMap<Acao, Double> guardador = new HashMap<Acao, Double>();
        double x = Double.MAX_VALUE;
        int melhorAção = estadoCorrente.getBestAction();
        //int melhorAçãoaux = estadoCorrente.getBestAction();
        if (melhorAção == -1) {
            melhorAção = 0;
        }
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            somatudo = 0;
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da politica a
            {
                Transicao trans = it.next();
                double prob = trans.getProbA();
                double Vtransicao = Vlinha.get(trans.getState());
                //  somatudo+=(prob*(a.getCost()+(p.getDiscount()* Vtransicao)));
                somatudo += (prob * Vtransicao);
            }
            somatudo += (a.getCost());
            // double calculaQsa = Vlinha.get(estadoCorrente) + somatudo;
            guardador.put(a, somatudo);
        }
        Acao guardamelhor = null;
        int melhorestado = 0;
        for (int o = 0; o < size; o++) {
            Acao h = estadoCorrente.getAction(o);
            if (x > guardador.get(h)) {
                x = guardador.get(h);
                guardamelhor = h;
                melhorestado = o;
            }
        }
        if (guardamelhor != null) {
            // if (melhorAçãoaux==-1) {
            //  naomudouPolitica = false;
            // }
            if (estadoCorrente.getAction(melhorAção).getName().equals(guardamelhor.getName()) == false) {
                naomudouPolitica = false;
            }
        }
        estadoCorrente.setBestEdge(melhorestado);
        return naomudouPolitica;


    }

    private boolean minUtilityLAO(Estado estadoCorrente, HashMap<Estado, Double> Vlinha, HashMap<Estado, HashMap<Acao, Double>> Qlinha ) {
        //int bestAction = -1;
        //  double Q = 0;
        double somatudo = 0;

        // int size = estadoCorrente.actionSize();
        //int melhorAçãoaux = estadoCorrente.getBestAction();
       /* HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();*/
        somatudo = 0;
        int melhorAção = estadoCorrente.getBestAction();
        if (melhorAção == -1) {
            melhorAção = 0;
        }

        for (Iterator<Transicao> it = estadoCorrente.getAction(melhorAção).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
        {

            Transicao trans = it.next();
            double prob = trans.getProbA();
            // double Vtransicao = Vlinha.get(trans.getState());
            double Vtransicao = Vlinha.get(trans.getState());
            double Vanterior = Vlinha.get(estadoCorrente);//qanterior
            // double Vtransicao = trans.getState().getCost();
            //double Vanterior = Vlinha.get(estadoCorrente);//qanterior
            //double Vanterior = estadoCorrente.getCost();//qanterior


            double X = calculaX(estadoCorrente.getAction(melhorAção).getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
            qtdeILAO2++;
            somatudo += prob * X;
        }
        somatudo = somatudo * p.getTamanhoPasso();
        double calculaQsa = estadoCorrente.getCost() + somatudo;
        //double calculaQsa = Q + somatudo;

        //Vlinha.put(estadoCorrente, calculaQsa);


        estadoCorrente.setCost(calculaQsa);//V
        int size = estadoCorrente.actionSize();

        //HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        HashMap<Acao, Double> t = new HashMap<Acao, Double>();
        int tamanho = estadoCorrente.actionSize();
        for (int i = 0; i < tamanho; i++) {
if (estadoCorrente.getAction(i).getName().equals(estadoCorrente.getAction(melhorAção).getName()))
{
    t.put(estadoCorrente.getAction(i), calculaQsa);
}else {
    t.put(estadoCorrente.getAction(i), estadoCorrente.getAction(i).getQ());
}  }
        Qlinha.put(estadoCorrente, t);
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);

            double comparator = Qlinha.get(estadoCorrente).get(a);
            if (calculaQsa > comparator)//se V<minQ(s,a)
            {
return true;

            }


        }
        return false;
    }

    private void minUtilityLAOvAntiga(Estado estadoCorrente, HashMap<Estado, Double> Vlinha) {
        //int bestAction = -1;
        //  double Q = 0;
        double somatudo = 0;

        // int size = estadoCorrente.actionSize();
        //int melhorAçãoaux = estadoCorrente.getBestAction();
       /* HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();*/
        somatudo = 0;
        int melhorAção = estadoCorrente.getBestAction();
        if (melhorAção == -1) {
            melhorAção = 0;
        }

        for (Iterator<Transicao> it = estadoCorrente.getAction(melhorAção).getTransitions().iterator(); it.hasNext(); ) //itera por todas transições da politica a
        {

            Transicao trans = it.next();
            double prob = trans.getProbA();
            // double Vtransicao = Vlinha.get(trans.getState());
            double Vtransicao = Vlinha.get(trans.getState());
            double Vanterior = Vlinha.get(estadoCorrente);//qanterior
            // double Vtransicao = trans.getState().getCost();
            //double Vanterior = Vlinha.get(estadoCorrente);//qanterior
            //double Vanterior = estadoCorrente.getCost();//qanterior


            double X = calculaX(estadoCorrente.getAction(melhorAção).getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
            qtdeILAO2++;
            somatudo += prob * X;
        }
        somatudo = somatudo * p.getTamanhoPasso();
        double calculaQsa = estadoCorrente.getCost() + somatudo;
        //double calculaQsa = Q + somatudo;

        //Vlinha.put(estadoCorrente, calculaQsa);
        estadoCorrente.setCost(calculaQsa);//V
        int size = estadoCorrente.actionSize();
        //int melhorAçãoaux = estadoCorrente.getBestAction();
        HashMap<Acao, Double> guardaQantesAtualizar = new HashMap<>();
        //for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
          //  Acao a = estadoCorrente.getAction(i);

           // double comparator = Qlinha.get(estadoCorrente).get(a);
            //if (calculaQsa > comparator) {
             //   return false;
            //}


        //}
        //return //true;
    }

    private boolean minUtilityLAO2(Estado estadoCorrente, HashMap<Estado, Double> Vlinha) {
        //int bestAction = -1;
        boolean naomudouPolitica = true;
        double somatudo = 0;
        int size = estadoCorrente.actionSize();
        HashMap<Acao, Double> guardador = new HashMap<Acao, Double>();
        double x = Double.MAX_VALUE;
        int melhorAção = estadoCorrente.getBestAction();
        //int melhorAçãoaux = estadoCorrente.getBestAction();
        if (melhorAção == -1) {
            melhorAção = 0;
        }
        for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
            Acao a = estadoCorrente.getAction(i);
            LinkedList<Transicao> t = a.getTransitions();
            somatudo = 0;
            for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da politica a
            {
                Transicao trans = it.next();
                double prob = trans.getProbA();
                double Vtransicao = Vlinha.get(trans.getState());
                double Vanterior = Vlinha.get(estadoCorrente);//qanterior
                double X = calculaX(a.getCost(), p.getDiscount(), Vtransicao, Vanterior, p.getK());
                qtdeILAO2++;
                somatudo += prob * X;
            }
            somatudo = somatudo * p.getTamanhoPasso();
            //double calculaQsa = Vlinha.get(estadoCorrente) + somatudo;
            guardador.put(a, somatudo);
            //   Vlinha.put(estadoCorrente, calculaQsa);
            //   estadoCorrente.setCost(calculaQsa);//V
        }
        Acao guardamelhor = null;
        int melhorestado = 0;
        for (int o = 0; o < size; o++) {
            Acao h = estadoCorrente.getAction(o);
            if (x > guardador.get(h)) {
                x = guardador.get(h);
                guardamelhor = h;
                melhorestado = o;
            }
        }
        if (guardamelhor != null) {
            // if (melhorAçãoaux==-1) {
            //  naomudouPolitica = false;
            // }
            if (estadoCorrente.getAction(melhorAção).getName().equals(guardamelhor.getName()) == false) {
                naomudouPolitica = false;
            }
        }
        estadoCorrente.setBestEdge(melhorestado);
        return naomudouPolitica;


    }




    public double calculaX(double recompensaSA, double fatorDesconto, double qislinhaa, double qisa, double k) {
        double x = 0;
        x = recompensaSA + (fatorDesconto * qislinhaa) - qisa;
        if (x < 0) {
            x = (1 - k) * x;
        } else {
            x = (1 + k) * x;
        }
        return x;
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
                double QMax = Double.POSITIVE_INFINITY;

                int size = estadoCorrente.actionSize();
                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    double somatudo = 0;
                    Acao a = estadoCorrente.getAction(i);
                    LinkedList<Transicao> t = a.getTransitions();
                    somatudo = 0;

                    for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                    {
                        Transicao trans = it.next();
                        int tamanho = trans.getState().actionSize();
                        double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                        Q = Qlinha1.get(estadoCorrente).get(a);//qanterior

                        for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.
                            Acao a2 = trans.getState().getAction(i2);
                            //double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                            double qcomparadorQlinha2 = Qlinha1.get(trans.getState()).get(a2);
                            //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                            if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
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
                    if (Double.compare(QMax, calculaQsa) > 0)//pega min
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
        String Diretorios = "Problema do Rio alto epsilonteste1234/";
        imprimedireçãoQ3(x, y, nome, Diretorios);

    }

    public void iterterationValue2(Double minError, int x, int y) {
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


                double Qmin = Double.POSITIVE_INFINITY;
                for (int i = 0; i < s.actionSize(); i++) {//itera por todas acoes s
                    Acao a = s.getAction(i);//pega ação
                    if (Qmin > Q3.get(s).get(a))
                        Qmin = Q3.get(s).get(a);

                }
                for (int i = 0; i < s.actionSize(); i++) {

                    t.put(s.getAction(i), Qmin);
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
                double QMax = Double.POSITIVE_INFINITY;

                int size = estadoCorrente.actionSize();
                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    double somatudo = 0;
                    Acao a = estadoCorrente.getAction(i);
                    LinkedList<Transicao> t = a.getTransitions();
                    somatudo = 0;

                    for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                    {
                        Transicao trans = it.next();
                        int tamanho = trans.getState().actionSize();
                        double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                        Q = Qlinha1.get(estadoCorrente).get(a);//qanterior

                        for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.
                            Acao a2 = trans.getState().getAction(i2);
                            //double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                            double qcomparadorQlinha2 = Qlinha1.get(trans.getState()).get(a2);
                            //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                            if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
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
                    if (Double.compare(QMax, calculaQsa) > 0)//pega min
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
/*
              String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2ffdafdfdfdfdfds.txt";
            String Diretorios = "Problema do Rio alto epsilonteste1234/";
            imprimedireçãoQ3(x,y,nome,Diretorios);
*/
    }

    public void iterterationValue3(Double minError, int x, int y) {
        Map<Integer, Estado> estados = this.p.getEstados();
        Q3 = new HashMap<>();
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
                this.Q3.put(s, t);


            }
        }

        do {

            HashMap<Estado, HashMap<Acao, Double>> Qlinha1 = new HashMap<>();//map para guardar o Qanterior.
            for (Estado s : p.getEstados().values()) {

                HashMap<Acao, Double> t = new HashMap<Acao, Double>();
                int tamanho = s.actionSize();
                for (int i = 0; i < tamanho; i++) {

                    t.put(s.getAction(i), this.Q3.get(s).get(s.getAction(i)));
                }
                Qlinha1.put(s, t);
            }
            auxresidual = Double.MIN_VALUE;
            for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
                Integer k = it2.next();
                double residualEstados = 0.0;
                Estado estadoCorrente = estados.get(k);
                double erroVanterior = V.get(estadoCorrente);
                double QMax = Double.POSITIVE_INFINITY;

                int size = estadoCorrente.actionSize();
                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    double somatudo = 0;
                    Acao a = estadoCorrente.getAction(i);
                    LinkedList<Transicao> t = a.getTransitions();
                    somatudo = 0;

                    for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                    {
                        Transicao trans = it.next();
                        int tamanho = trans.getState().actionSize();
                        double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                        Q = Qlinha1.get(estadoCorrente).get(a);//qanterior

                        for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.
                            Acao a2 = trans.getState().getAction(i2);
                            //double qcomparadorQlinha2 = a2.getQ();//recupera MAX Q(s',a)
                            double qcomparadorQlinha2 = Qlinha1.get(trans.getState()).get(a2);
                            //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                            if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
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
                    if (Double.compare(QMax, calculaQsa) > 0)//pega min
                    {
                        QMax = calculaQsa;
                    }
                    this.Q3.get(estadoCorrente).put(a, calculaQsa);
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
/*
              String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2ffdafdfdfdfdfds.txt";
            String Diretorios = "Problema do Rio alto epsilonteste1234/";
            imprimedireçãoQ3(x,y,nome,Diretorios);
*/
    }

    public void valueIterationHeuristic2(Double minError, int x, int y) {
        Map<Integer, Estado> estados = this.p.getEstados();
        Q3 = new HashMap<>();
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
                this.Q3.put(s, t);


            }
        }


        do {

            HashMap<Estado, HashMap<Acao, Double>> Qlinha1 = new HashMap<>();//map para guardar o Qanterior.
            for (Estado s : p.getEstados().values()) {

                HashMap<Acao, Double> t = new HashMap<Acao, Double>();
                int tamanho = s.actionSize();
                for (int i = 0; i < tamanho; i++) {

                    t.put(s.getAction(i), this.Q3.get(s).get(s.getAction(i)));
                }
                Qlinha1.put(s, t);
            }
            auxresidual = Double.MIN_VALUE;
            for (Iterator<Integer> it2 = estados.keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
                Integer k = it2.next();
                double residualEstados = 0.0;
                Estado estadoCorrente = estados.get(k);
                String name = estadoCorrente.getName();
                double erroVanterior = V.get(estadoCorrente);
                double QMax = Double.POSITIVE_INFINITY;

                int size = estadoCorrente.actionSize();
                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    // double somatudo = 0;
                    Acao a = estadoCorrente.getAction(i);
                    LinkedList<Transicao> t = a.getTransitions();
                    //somatudo = 0;
                    double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                    for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                    {
                        Transicao trans = it.next();
                        int tamanho = trans.getState().actionSize();

                        // Q = Qlinha1.get(estadoCorrente).get(a);//qanterior

                        for (int i2 = 0; i2 < tamanho; i2++) {//pega o menor Q entre as acoes possiveis.
                            Acao a2 = trans.getState().getAction(i2);
                            //double qcomparadorQlinha2 = a2.getQ();//recupera MIN Q(s',a)
                            double qcomparadorQlinha2 = Qlinha1.get(trans.getState()).get(a2);
                            //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                            if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
                            {
                                qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o min dos Q(s',a)
                            }
                        }
                    }

                    double calculaQsa = a.getCost() + qcomparadorQlinha;
                    qtdedet2++;
                    // if (Qmax < calculaQsa) {
                    // if (Qmax > calculaQsa) {//pega o Qmax
                    //  if(Double.compare(Qmax,calculaQsa)<0)//pega max
                    if (Double.compare(QMax, calculaQsa) > 0)//pega min
                    {
                        QMax = calculaQsa;
                    }
                    this.Q3.get(estadoCorrente).put(a, calculaQsa);
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
            qtdedet++;
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

      /*  String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " 2.txt";
        String Diretorios = "Problema do Rio alto epsilonteste1/";
        imprimedireçãoQ3(x,y,nome,Diretorios);
*/
    }


    public void valueIterationHeuristic(Double minError, int x, int y) {
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
                String name = estadoCorrente.getName();
                double erroVanterior = V.get(estadoCorrente);
                double QMax = Double.POSITIVE_INFINITY;

                int size = estadoCorrente.actionSize();
                for (int i = 0; i < size; i++) {//itera por todas as acoes do estado s
                    // double somatudo = 0;
                    Acao a = estadoCorrente.getAction(i);
                    LinkedList<Transicao> t = a.getTransitions();
                    //somatudo = 0;
                    double qcomparadorQlinha = Double.POSITIVE_INFINITY;
                    for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições da açao a
                    {
                        Transicao trans = it.next();
                        int tamanho = trans.getState().actionSize();

                        // Q = Qlinha1.get(estadoCorrente).get(a);//qanterior

                        for (int i2 = 0; i2 < tamanho; i2++) {//pega o menor Q entre as acoes possiveis.
                            Acao a2 = trans.getState().getAction(i2);
                            //double qcomparadorQlinha2 = a2.getQ();//recupera MIN Q(s',a)
                            double qcomparadorQlinha2 = Qlinha1.get(trans.getState()).get(a2);
                            //    if (qcomparadorQlinha < qcomparadorQlinha2) {
                            if (Double.compare(qcomparadorQlinha, qcomparadorQlinha2) > 0)//guarda  min
                            {
                                qcomparadorQlinha = qcomparadorQlinha2;//qcomparadorQlinha guarda o min dos Q(s',a)
                            }
                        }
                    }

                    double calculaQsa = a.getCost() + qcomparadorQlinha;
                    qtdedet2++;
                    // if (Qmax < calculaQsa) {
                    // if (Qmax > calculaQsa) {//pega o Qmax
                    //  if(Double.compare(Qmax,calculaQsa)<0)//pega max
                    if (Double.compare(QMax, calculaQsa) > 0)//pega min
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
            qtdedet++;
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

   /*     String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " 2.txt";
        String Diretorios = "Problema do Rio alto epsilonteste1/";
        imprimedireçãoQ3(x,y,nome,Diretorios);*/

    }

    void imprimeSIRTEmFormaDeMatriz(int n, int tamanhoMAxS, String nomeArquivo, String diret) {

        //  Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA,diret);

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
            // double v = Double.NEGATIVE_INFINITY;//min
            double v = Double.POSITIVE_INFINITY;//max
            String nome = null;
            for (int i = 0; i < n; i++) {
                //if (v < b[i] == true)//max
                if (v > b[i] == true)//min
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
                //arquivoSaida.escreveArquivo2(a[i][j]);
            }
            System.out.println();
            //  arquivoSaida.escreveArquivo("");
        }
        //  arquivoSaida.fechaArquivo();

    }


    public void imprimedireçãoQ3(int x, int z, String nomeArquivo, String diret) {
        Arquivo2 arquivoSaida = new Arquivo2(nomeArquivo, Arquivo2.ESCRITA, diret);
        arquivoSaida.escreveArquivo("k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso());
        String[][] a = new String[x][z];
        int y1 = x;
        for (int i = 0; i < x; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {

            int i1 = 0;

            for (int y = 0; y < z; y++) {


                i1 = i1 + 1;
                a[i][y] = "x" + i1 + "y" + y1;
                //System.out.print(a[i][y]+"  ");
            }

            y1 = y1 - 1;
        }
        System.out.println();
        System.out.println();
        for (int i = x - 1; i >= 0; i--) {
            System.out.println();
            arquivoSaida.escreveArquivo("");

            for (int y = 0; y < z; y++) {
                Estado s = p.getEstado(a[i][y]);
                HashMap<Acao, Double> t = Q2.get(s);
                int size = t.size();
                double valor = Double.POSITIVE_INFINITY;
                String nome = null;

                for (int ç = 0; ç < size; ç++) {//itera por todas as acoes do estado s
                    Acao act = s.getAction(ç);
                    if (valor > t.get(act)) {
                        valor = t.get(act);
                        nome = act.getName();
                    }


                }


                if ((t.get(s.getAction(0)).equals(t.get(s.getAction(1)))) && (t.get(s.getAction(0)).equals(t.get(s.getAction(2)))) && (t.get(s.getAction(0)).equals(t.get(s.getAction(3))))) {
                    if (t.get(s.getAction(0)).equals(0.0)) {
                        System.out.print("0");
                        arquivoSaida.escreveArquivo2("0");
                    } else {
                        System.out.print("+");
                        arquivoSaida.escreveArquivo2("+");
                    }
                } else if (nome.equals("move-E")) {
                    System.out.print("→");
                    arquivoSaida.escreveArquivo2("→");
                } else if (nome.equals("move-W")) {
                    System.out.print("←");
                    arquivoSaida.escreveArquivo2("←");
                } else if (nome.equals("move-N")) {
                    System.out.print("↑");
                    arquivoSaida.escreveArquivo2("↑");
                } else if (nome.equals("move-S")) {
                    System.out.print("↓");
                    arquivoSaida.escreveArquivo2("↓");
                }

            }

        }


        arquivoSaida.escreveArquivo("");

        for (int i = x - 1; i >= 0; i--) {
            System.out.println();

            arquivoSaida.escreveArquivo("");

            for (int y = 0; y < z; y++) {
                Estado s = p.getEstado(a[i][y]);
                arquivoSaida.escreveArquivo2(s.getName());
                arquivoSaida.escreveArquivo("");
                HashMap<Acao, Double> t = Q2.get(s);
                int size = t.size();
                double valor = Double.POSITIVE_INFINITY;
                String nome = null;

                for (int ç = 0; ç < size; ç++) {//itera por todas as acoes do estado s
                    Acao act = s.getAction(ç);
                    if (valor > t.get(act)) {
                        valor = t.get(act);
                        nome = act.getName();
                    }
                    arquivoSaida.escreveArquivo2(act.getName() + "  " + t.get(act));


                }
                arquivoSaida.escreveArquivo("");
            }
        }

        arquivoSaida.fechaArquivo();
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
