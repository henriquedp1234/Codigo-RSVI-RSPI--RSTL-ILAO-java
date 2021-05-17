package Problem;
//Sir sem estado meta para calcular variancia
import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.Description.Transicao;
import org.apache.commons.math3.distribution.BinomialDistribution;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Henri on 14/07/2019.
 */
public class criaProblemaSIRsemmet {
    public Problem p;
    double beta;
    double lambda;
    double costvaccine;
    double costinf;
    int n;

    public criaProblemaSIRsemmet(int s, int i, int r, double beta, double lambda, double costvaccine, double costinf, Problem p, double exp) {
      //  String nome = "s=" + s + " i=" + i + " r=" + r;
      //  Estado e = new Estado(nome);
      //  p.setInitialState(nome);
        this.beta = beta;
        this.lambda = lambda;
        this.costinf = costinf;
        this.costvaccine = costvaccine;
        this.p=p;
        n = s + i + r;
criaTodosEstados(s,i,r);
        geraTodasAcoes2ComProbe2Binomiais2custoexp(11,exp);
    }


    void geraAção(Estado estadoAtual) {

    }

    double calculatransicaos(Estado estadoAtual, Transicao a) {


        return (estadoAtual.getS() - beta * estadoAtual.getS() * estadoAtual.getI() - (a.getProbA() * estadoAtual.getS()));
    }

    double calculaTransiçãor(Estado estadoAtual, Transicao a){
     //   r′=(r+λ·i+π(ν)·s)
        return (estadoAtual.getR()+ lambda *estadoAtual.getI()+a.getProbA()*estadoAtual.getI());
    }
    double calculaTransiçãoi(Estado estadoAtual, Transicao a)
    {
      //  i′=(i+β·s·i−λ·i)

        return (estadoAtual.getI()+beta*estadoAtual.getS()*estadoAtual.getI()-lambda*estadoAtual.getI()) ;
    }
    double calculaCusto(Estado estadoAtual, Transicao a)
    {
        return (estadoAtual.getS()*(-costvaccine * a.getProbA()+(1-a.getProbA())))-costinf*estadoAtual.getI()+estadoAtual.getR()*costinf;
    }


    void criaTodosEstados(int s, int i, int r)

    {

        Map<Integer, Estado> estados = new HashMap<Integer, Estado>();
        for (int l = 0; l <= n; l++) {
            for (int m = 0; m <= n; m++) {
                for (int k = 0; k <= n; k++) {
                    if ((l + m + k) == n) {
                        String nome = "s=" + (double) l + " i=" + (double) m + " r=" + (double) k;
                        // String nome = "s=" + l+ " i=" + m + " r=" + k;
                        Estado e = new Estado(nome,0);
                        e.setS(l);
                        e.setI(m);
                        e.setR(k);
                      estados.put(nome.trim().hashCode(), e);

                    }
                }
            }
        }
        p.adicionaEstados(estados);
    }


    void geraTodasAcoes2ComProbe2Binomiais2custoexp(int numeroDeAcoes,double exp) {
//p.setInitialState("s=27.0 i=3.0 r=0.0");
        p.setInitialState("s=150.0 i=0.0 r=0.0");
      //  p.setInitialState("s=90.0 i=10.0 r=0.0");
    //    p.setInitialState("s=100.0 i=0.0 r=0.0");
       // p.setInitialState("s=10.0 i=0.0 r=0.0");
        //p.setFinalState("s=0.0 i=0.0 r=100.0");

        for ( Estado s2 : this.p.getEstados().values()) {
            if (s2.getI()==0.0) {
                p.setFinalState(s2.getName());
            }
        }
        for ( Estado s : this.p.getEstados().values()) {
            for (int i = 1; i <= numeroDeAcoes; i++) {
                String name = "acao" + i;
                Acao a = new Acao(name);
                double numac = numeroDeAcoes;
                double aAuxiliar=0;
                if (i==1) {
                     aAuxiliar = 0;
                }
                else
                {
                     aAuxiliar = 1 / (numac-1);
                }
                a.setActionProbabilityproDominio((i-1) * aAuxiliar);
                a.setCost(calculaCusto3(s, a, exp,100/n));
                double cost = a.getCost();
                double x=((i-1)*aAuxiliar)*s.getS();
                double n=s.getS()-x;
                int nfloor= (int) (s.getS()- Math.floor(x));
                int nceil= (int) (s.getS()- Math.ceil(x));
                double mfloor= Math.floor(((i-1)*aAuxiliar)*s.getS());
                double mceil= Math.ceil(((i-1)*aAuxiliar)*s.getS());
                double mtrue= ((i-1)*aAuxiliar)*s.getS();
                double qfloor=Math.floor(lambda*s.getI());
                double qceil=Math.ceil(lambda*s.getI());
                double qtrue=(lambda*s.getI());
                double probp = (beta*(s.getI())/(this.n));//gera probabilidade
                double probqflorr=mtrue-mfloor;
                double probqceil=mceil-mtrue;
                double probzflorr=qtrue-qfloor;
                double probzceil=qceil-qtrue;
                //a.setActionName("acao" + i  );
                Acao a2 = new Acao(name);
                s.addAcao(a2);
                if (s.getI()==0 )
                {
                    probqflorr=0;
                }
                for (int x1=(int)mfloor;x1<=(int)mceil;x1++) {
                    int nbin=(int)s.getS()-x1;
                    for (int y1 = 0; y1 <= nbin; y1++) {


                        for (int z1 = (int) qfloor; z1 <= (int) qceil; z1++) {
                            double ss = s.getS() - x1 - y1;
                            double ii = s.getI() + y1 - z1;
                            double rr = s.getR() + x1 + z1;


                            if (ss+rr+ii==this.n )//estado gerado == tamanho
                            {
                                String nome = "s=" + ss + " i=" + ii + " r=" + rr;
                                Estado sucessor = this.p.getEstado(nome);
                                //   double binomial=retornaA(nfloor, probp, y1);//gera binomial
                                // nbin=(int)s.getS()-x1;
                                double binomial=retornaA(nbin, probp, y1);//gera binomial
                                //   double binomialfloor=retornaA(nfloor, probp, y1);//gera binomial
                                //  double binomialceil=retornaA(nceil, probp, y1);//gera binomial

                                //a2.setActionName("acao" + i);
                                a2.setCost(cost);
                             //   a2.setCurrentState(s);
                                double prob=0;
                                double probx=0;
                                double probz=0;

                                if (x1==(int)mfloor)
                                {
                                    probx=(mfloor+1)-mtrue;
                                }
                                else {
                                    probx=mtrue-mfloor;
                                }
                                //if(probx==0)
                                // {
                                //    probx=1;
                                //}
                                if (z1==qfloor)
                                {
                                    probz=(qfloor+1)-qtrue;
                                }else
                                {
                                    probz=qtrue-qfloor;
                                }
                                // if (probz==0)
                                //{
                                //  probz=1;
                                // }

                                //calculo da probabilidade
                                prob=((probx)*binomial*(probz));
                                //prob=(probqflorr*binomialfloor*probzflorr)+(probqceil*binomialceil*probzceil);
                                //prob=(probqflorr*binomialfloor*probzflorr)+(probqceil*binomialceil*probzceil);
                                // prob=(probx*binomial*probz);
                                Acao[] t=null;
                                //t = s.getAction("acao" + i);
                                t = s.getActions();
                                if (sucessor.isGoal()==true)
                                {

                                }
                                else{


                                //int tamanho = s.getAction("acao" + i).size();
                                if (s.actionSize()>0&&s.verificaacao("acao" + i)==true)//verifica se existe a ação:
                                {
                                    if (t[i-1].TransSize()==0)
                                    {
                                        a2.adicionaTransicao(sucessor,prob,0);
                                    }
                                    else{
                                        int tam2= s.getActionPorNome("acao" + i).getTransitions().size();
                                        LinkedList<Transicao> ttt =  t[i-1].getTransitions();
                                        boolean tem=false;
                                        for (int y=0;y<tam2;y++) {

                                            if (ttt.get(y).getState().getName().equals(nome))//verifica se sucessor é o mesmo, se for é a mesma ação
                                            // if (t[u].getTransitions().get(y).getState().getName().)
                                            {tem=true;
                                                double probabilidadecomplementar = t[i-1].getTransitions().get(y).getProbA();
                                                double probabilidadecomplementar2 = prob + probabilidadecomplementar;
                                                t[i-1].getTransitions().get(y).setProbA(probabilidadecomplementar2);
                                                double trtrtrrtttttrt=1;
                                                break;

                                            }
                                        }
                                        if (tem ==false)
                                        {
                                            a2.adicionaTransicao(sucessor,prob,0);
                                    }
                                    }
                                    }
                                }
                        }
                    }
                }
            }
        }


    }
    }

    double calculaCusto3(Estado estadoAtual, Acao a,double exp,double Factor)
    //R (costinf, costvaccine, s, i, r, π(ν)) = (s · (−costvaccine · π(ν) + (1−π(ν))))−costinf ·i+r. costinf
    {/*double e=estadoAtual.getS();
    double f=(-costvaccine*a.getActionProbability())+(1-a.getActionProbability());
    double g=(-costinf*estadoAtual.getI())+(estadoAtual.getR()*costinf);*/
        //double e = (Math.pow(estadoAtual.getS(), exp) * (-costvaccine * a.getActionProbabilityproDominio())) - costinf * estadoAtual.getI();// usado nos testes anteriores
        // R(s,a) = -cost_{vac}a*X_S - cost_inf(X_I)^{1.5}

        //   double e= (-costvaccine*(a.getActionProbabilityproDominio()*estadoAtual.getS()))- (Math.pow(costinf, exp)); //dif2


        // R(s,a) = -cost_{vac}(a*X_S)^{1.5} - cost_inf(X_I)^{1.5}
        // double e= (-costvaccine*Math.pow((a.getActionProbabilityproDominio()*estadoAtual.getS()),exp))- (Math.pow(costinf, exp)); dif1

        //double f= -costinf*estadoAtual.getI()+estadoAtual.getR()*costinf;
        //return e+f;
      //  double e= (-costvaccine*((Math.pow(a.getActionProbabilityproDominio()*(estadoAtual.getS()*Factor), exp)))- (costinf* (estadoAtual.getI()*Factor)));// para usar
        double e= (-costvaccine*((Math.pow(a.getActionProbabilityproDominio()*(estadoAtual.getS()), exp)))- (costinf* (estadoAtual.getI())));
          //double e =(estadoAtual.getS()*(-costvaccine*a.getActionProbabilityproDominio()+(1-a.getActionProbabilityproDominio())))*(-costinf*estadoAtual.getI())+(estadoAtual.getR()*costinf);
        return e;
    }


    double retornaA(int m, double p, int j) {
        //smile.stat.distribution.BinomialDistribution b = new smile.stat.distribution.BinomialDistribution(n,p);
        // BigDecimal sss= new BigDecimal(m);
        //sss = sss.setScale(0, RoundingMode.HALF_UP);
        //int m1= sss.intValueExact();

        BinomialDistribution b = new BinomialDistribution(m, p);

        double result = b.probability(j);

        return result;
    }

}
