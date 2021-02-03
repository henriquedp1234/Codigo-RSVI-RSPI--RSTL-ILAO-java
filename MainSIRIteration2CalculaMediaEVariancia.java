import Problem.Arquivo;
import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.criaProblemaSIR;
import Problem.criaProblemaSIRsemmet;
import Problem.criaProblemaSIRsemmetv2;
import Problem.criaProblemaSIRvariasMeta;
import Problem.criaProblemaSIR3;
import java.util.HashMap;
import Problem.cenarioNovo;
class MainSIRIteration2CalculaMediaEVariancia {
    private static final int SIZE = 1;

    public static void main_ORIGINAL(String args[]) {
        //if(args.length != 1)
        //{
        //	System.out.println("Um parametro eh necessario.");
        //	System.out.println("Ex.: Main [arquivo de entrada]");
        //	return;
        //}
        Arquivo arquivoEntrada = null;
        //arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
        //arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
        //arquivoEntrada = new Arquivo("river_3x3.net", Arquivo.LEITURA);

        //Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);

        Cronometro cT = new Cronometro();
        //Problem gW = null;

        System.out.println();

        //LAO lao = null;
        //LAO lao2= null;
        //LAO2 lao2 = null;
        LAOSIRT2 lao2 = null;

        Problem p = new Problem(1);

        int x = 10;
        int y = 10;

        int costinf[] = { 4,4,4,4,4,4,4,4,4};
       int costv[] = {1,1,1,1,1,1,1,1,1};
       //  int costinf[] = { 4,4,4,4,4,4,4,4,4};

        //int costinf[] = {1, 4, 1};
      //   double bet[] = {0.25};
     // double lambda[] = {0.25};
       // int costv[] = {10, 10, 10,10, 10, 10,10, 10, 10};
        //int costinf[] = {40,40,40,40,40,40,40,40,40,40,40,40};
        //double bet[] =    {0.75,0.8,0.25,0.25,0.2};
        //double lambda[] = {0.25,0.4,0.25,0.75,0.25};

        double bet[] =    {0.75,0.8,0.25,0.2,0.25};
        double lambda[] = {0.25,0.4,0.25,0.25,0.75};
        double k[] = {0.9,0.8,-0.8,-0.5,0.5,-0.9};
       // double lambda[] = {1,1,1};
      //  double lambda[] = {1.0, 1.0, 1.0};

       // double k[] = {0.9,-0.9};v
       // double exp[]  = {1.1,1.2,1.3,1.4,1.5};
        for (int i = 0; i < SIZE; i++) {


            double averageTime = 0;
            //LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
            //criaProblemaIgorpositivo2 c2 = new criaProblemaIgorpositivo2(3,3,0.8,p);resto 0
            //criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(x,y,0.8,p);//meta 0 resto 1
            //	criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(7,15,0.8,p);//meta 0 resto -1
            //	criaProblemaIgorNegativonovo c = new criaProblemaIgorNegativonovo(7,10,0.8,p);//meta -1 resto 0
           // for (int q = 0; q < 5; q++) {
            for (int l = 0; l < 7; l++) {
            for (int j = 0; j < 1; j++) {
            int tam = 100;
            double exp = 1.5;
           // criaProblemaSIR c = new criaProblemaSIR(tam, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p, exp[q]);
                criaProblemaSIRvariasMeta c = new criaProblemaSIRvariasMeta(tam, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p, exp); //problema usando !!!!!!!!!!!!!!!!
      //  cenarioNovo c = new cenarioNovo(p);

          //  criaProblemaSIR c = new criaProblemaSIR(10, 0, 0, 0.75, 0.25, 1, 4, p, exp);
            //criaProblemaSIR c = new criaProblemaSIR(10, 0, 0, 0.25, 0.75, 1, 4, p, exp);
            //criaProblemaSIR c = new criaProblemaSIR(30, 0, 0, 0.5, 0.5, 1,1, p, 1.5);
            //criaProblemaSIR c = new criaProblemaSIR(10, 0, 0, 0.6, 0.4, 1,1, p, 1.25);
            System.out.println("Lendo Arquivo... ");

            //gW = lGW.executa(-1,0);

            System.out.println("Leitura Concluida!");


            //lao2 = new LAO(p,0,0);
            lao2 = new LAOSIRT2(p, 1, 0);
            //lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!

            System.out.println("\nExecutando LAO*...");

            cT.start();
            //cT.start();
            //gW.setDiscount(0.99);

           // p.setK(0.99);
            //lao.executa(LAO.VALUE);
                p.setDiscount(1);
                p.setK(k[l]);

            lao2.iterterationValue(0.00000000000001, x, y);

                cT.stop();
                double averageTempo=cT.getTempo();


                HashMap<Estado,Acao> politica=new HashMap<Estado,Acao> ();
                String nome ="tam="+tam+ " costv" + costv[j] + "costinf =" + costinf[j] + "bet" + bet[j] + "lambda " + lambda[j] + " k= " + k[l] + "vfinal2.txt";
                //String DirName = "new.txt";
                //String DirName = "S= " + tam + "exp= " + exp[q];
                String DirName = "S= " + tam + "exp= " + exp + "testeporcentagemvfinal";

                lao2.imprimeSIRTEmFormaDeMatriz22(11, tam+1, nome, DirName,politica);
                lao2.imprimeSIRTEmFormaDeMatriz(11, tam+1, nome, DirName,averageTempo);

                lao2.imprimeSIRTEmFormaDeMatrizV(11, tam+1, nome, DirName,averageTempo);
//lao2.putpolitica(politica);
            //String DirName = "new.txt";
            //String DirName = "S= " + tam + "exp= " + exp[q];

          //  String nome = "teste.txt";
//				arquivoEntrada = new Arquivo(nome, Arquivo.LEITURA);
                p.setK(0.0);
                String DirName2 = "mediavariameta99";
                String arqname = "media"+ nome;

                String DirName3 = "vetor";
                String arqname2 = "vetor"+ nome;
//                lao2.probEstado(politica,arqname2,DirName3);
                lao2.policyIteration(politica,1,1,arqname,DirName2);
                HashMap<Estado, Double> Var=new HashMap<>();
                HashMap<Estado, Double> VV=new HashMap<>();

                VV=lao2.V;

                String []guardanamoe =  new String[politica.size()];
                int z=0;
                for (Estado s : p.getEstados().values())
                {
                    guardanamoe[z]=politica.get(s).getName();
                    z++;
                }
                Problem p2 = new Problem(1);

        criaProblemaSIRsemmet c1 = new criaProblemaSIRsemmet(tam, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p2, exp);
                for (Estado s : p2.getEstados().values()) {//inicializar V

                    //System.out.println(k.toString()); //Key
                    //Estado s = estados.get(k);

                    Var.put(s, 0.0);

                    if (s.isGoal() == true) {
                        Var.put(s, 0.0);
                    }
                    //System.out.print( s.getName() ); //State Name
                }
                int mm=0;
                HashMap<Estado,Acao> politica2=new HashMap<Estado,Acao> ();
                  for (Estado s : p2.getEstados().values())
                {
                Acao a = politica.get(s);
                //politica.remove(s);
                Estado neww =p.getEstado(s.getName());

                politica2.put(s,p2.getEstado(neww.getName()).getActionPorNome(guardanamoe[mm]));
mm++;

                }

                 DirName2 = "Varianciavariasmetasfim99";
                 arqname = "Variancia"+ nome;
               lao2.policyEvaluationVariancia(0.00000000000001, 1, 1, politica2,Var,p,VV,p2,arqname,DirName2);//calcula Variância
         //   cT.stop();
           // int nn = 500;
            //lao2.calculaRecompensaAcumulada(nn);
            //	averageTime += cT.getTempo();

            System.out.println("\nExecucao " + (i + 1) + " de " + SIZE);
            System.out.println("Tempo medio " + averageTime / (i + 1));
            //	arquivoEntrada.fechaArquivo();
            	}
            }
        //}
            //String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2.txt";

            averageTime /= SIZE;

            System.out.println("\nPolitica:");
            //Arquivo arquivoSaida = new Arquivo(nome, Arquivo.ESCRITA);
            //lao.print(arquivoSaida);
            //lao2.print(arquivoSaida, x, y);
            //System.out.println("Quantidade Total de Estados: " + gW.sizeEstados());
            System.out.println("Quantidade Total de Estados: " + p.sizeEstados());
            //arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + gW.sizeEstados());
            //arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + p.sizeEstados());

            //System.out.println("\nTempo de execucao: " + cT.getTempo() + " ms");
            //arquivoSaida.escreveArquivo("Tempo de execucao: " + cT.getTempo() + " ms");
            System.out.println("\nTempo de execucao: " + averageTime + " ms");
            //arquivoSaida.escreveArquivo("Tempo de execucao: " + averageTime + " ms");

//		arquivoEntrada.fechaArquivo();
            //	arquivoSaida.fechaArquivo();
        }
    }


	/*public static void main_IV(String args[]) {

		//Arquivo arquivoEntrada = new Arquivo(args[0], Arquivo.LEITURA);
		Arquivo arquivoEntrada = null;
	//	Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);
		
		Cronometro cT = new Cronometro();	
		Problem gW = null;
		
		System.out.println("main_IV\n**********");
		
		arquivoEntrada = new Arquivo("river_7x5.net", Arquivo.LEITURA);
		LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
		
		System.out.println("Lendo Arquivo... " + "navegation_1.net");
		gW = lGW.executa();
		System.out.println("Leitura Concluida!");
		LAO lao = null;
		lao = new LAO(gW);
		
		System.out.println("\nExecutando LAO*...");
		cT.start();

		lao.executa(LAO.VALUE);
		cT.stop();
		
		System.out.println("\nExecutando IV...");
		cT.start();
		lao.superValueIterationLOG();
		cT.stop();
		System.out.println("\nTempo de execucao: " + cT.getTempo() + " ms");
		
		
		System.out.println("\nPolitica:");
        Arquivo arquivoSaida =new Arquivo("abc.txt", Arquivo.ESCRITA);
		lao.print(arquivoSaida);
		
		lao.pprint(3, 3, true);

	}
	*/

    public static void main(String args[]) {
        //System.out.println("ddd".hashCode());
        main_ORIGINAL(args);
        //main_IV
        //main_IV(args);
        return;
    }
}

