//Classe Implementação iteração de política no SIR, execução variando k, beta e lambda
import Problem.Arquivo;
import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.criaProblemaIgorpositivo3mudcost;
import Problem.criaProblemaSIRvariasMeta;

import java.util.HashMap;

class MainSIRPOLICYITERATION
{	
	private static final int SIZE = 1;
    
	public static void main_ORIGINAL(String args[])
    {
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
		

		//Problem gW = null;

		System.out.println();
		
		//LAO lao = null;
		//LAO lao2= null;
		LAOSIRT2 lao2= null;

		Problem p = new Problem(1);;
		int x=60;
		int y=70;
		double bet[] =    {0.2,0.75,0.8,0.25,0.25,};
		double lambda[] = {0.25, 0.25,0.4,0.25,0.75,};
		//double bet[] =    {0.2};
		//double lambda[] = {0.25};
		double k[] = {-0.9,0.9,0.0,0.8,0.5,-0.5,-0.8};
		int costinf[] = { 4,4,4,4,4,4,4,4,4};
		int costv[] = {1,1,1,1,1,1,1,1,1};
		double averageTime = 0;
		double averageTime2 = 0;
		for(int i = 0; i < SIZE; i++) {
			for (int l = 0; l < 7; l++) {
				for (int j = 0; j < 5; j++) {
					int tam = 100;
					double exp = 1.5;
					//arquivoEntrada = new Arquivo(, Arquivo.LEITURA);
					//LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
					//criaProblemaIgorpositivo2 c = new criaProblemaIgorpositivo2(3,3,0.8,p);//resto 0
					//	criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(x,y,0.8,p);//meta 0 resto 1			//
					//criaProblemaIgorpositivo4 c = new criaProblemaIgorpositivo4(x,y,0.8,p);
					// criaProblemaIgorpositivo3mudcost c = new criaProblemaIgorpositivo3mudcost(x,y,0.8,p,1,1,0.99);//meta 0 resto 1
					//	criaProblemaIgorVersãoVoltaInicio c= new criaProblemaIgorVersãoVoltaInicio(x,y,0.8,p,1,1,0.99);
					//criaProblemaIgorVersãoVoltaInicio2 c = new criaProblemaIgorVersãoVoltaInicio2(x,y,0.8,p,1.0,1,0.99);
					//criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(x,y,0.8,p);//meta 0 resto -1
					//	criaProblemaIgorNegativonovo c = new criaProblemaIgorNegativonovo(7,10,0.8,p);//meta -1 resto 0

					criaProblemaSIRvariasMeta c = new criaProblemaSIRvariasMeta(tam, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p, exp); //problema usando !!!!!!!!!!!!!!!!
					System.out.println("Lendo Arquivo... ");

					//gW = lGW.executa(-1,0);


					System.out.println("Leitura Concluida!");
					Cronometro cT = new Cronometro();
					averageTime = 0;
					averageTime2 = 0;
					//lao2 = new LAO(p,0,0);
					//lao2 = new LAOSIRT2(p,1,0,x,y);
					lao2 = new LAOSIRT2(p, 1, 0);
					//lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!

					System.out.println("\nExecutando LAO*...");
					System.out.println("rodando:" + "beta= "+ bet[j] +" lambda= " + lambda[j] + " k="+ k[l]);

					//cT2.start();
					//	lao2.valueIterationHeuristic2(0.00000000000001,x,y);//apenas quando usa heuristica.
					// p.setK(1.0);
					//cT2.stop();

					Cronometro cT2 = new Cronometro();
					p.setK(0.0);
					p.setTamanhoPasso(1 / (1 + Math.abs(0.0)));
					double desconto = 1;
					p.setDiscount(desconto);

					//	lao2.iterterationValue3(0.00000000000001,x,y);//apenas quando usa heuristica.
					cT.start();
					lao2.iterterationValue(0.00000000000001, x, y);
					cT.stop();
					String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2ffdafdfdfdfdfds.txt";
					String Diretorios = "desconto= " + p.getDiscount() + "/";
					HashMap<Estado, Acao> politica = new HashMap<Estado, Acao>();
					//lao2.imprimedireçãoQ4(x, y, nome, Diretorios,politica);
		/*	for (Iterator<Integer> it2 = p.getEstados().keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
				Integer k = it2.next();
				Estado estadoCorrente = p.getEstados().get(k);

				politica.put(estadoCorrente,estadoCorrente.getAction(2));
			}*/
					lao2.imprimeSIRTEmFormaDeMatriz22(11, tam+1, nome, Diretorios,politica);

					p.setK(k[l]);
					p.setTamanhoPasso(1 / (1 + Math.abs(k[l])));
					p.setDiscount(desconto);
					cT2.start();
					lao2.policyIteration2(politica, x, y);
					//lao2.policyIterationBelman(politica,10,7);
					// p.setK(1.0);
					cT2.stop();
					//	String nome2 = "PI.txt";
					//String Diretorios2 = "OI= " + p.getDiscount() + "/";
					//  lao2.imprimedireçãoQ3(x, y, nome2, Diretorios2);
					//p.setTamanhoPasso(1/(1+Math.abs(0.3)));
					//p.setTamanhoPasso(0.5);


					//cT.start();
					//gW.setDiscount(0.99);
					//p.setDiscount(1);
					//lao.executa(LAO.VALUE);
					//p.setTamanhoPasso(1/(1+Math.abs(-0.99)));
					//	p.setK(0.99);
					//   cT.start();
					//	lao2.iterterationValue2(0.00000000000001,x,y);
					//cT.stop();


					averageTime = cT.getTempo();
					averageTime2 = cT2.getTempo();

					String nome2 = "POLITICAAAA2   tam=" + tam + " costv" + costv[j] + "costinf =" + costinf[j] + "bet" + bet[j] + "lambda " + lambda[j] + " k= " + k[l] + "vfinal2.txt";
					//String DirName = "new.txt";
					//String DirName = "S= " + tam + "exp= " + exp[q];
					String DirName2 = "S= " + tam + "exp= " + exp + "testeporcentagemvfinal";

					//lao2.imprimeSIRTEmFormaDeMatriz(11, tam+1, nome2, DirName2,(averageTime + averageTime2));
					lao2.imprimeSIRTEmFormaDeMatrizpolitica(11, tam+1, nome2, DirName2,politica,p,(averageTime + averageTime2),averageTime,averageTime2);

					System.out.println("\nExecucao " + (i + 1) + " de " + SIZE);
					System.out.println("Tempo medio " + averageTime / (i + 1));
//			arquivoEntrada.fechaArquivo();
				}
			}
		}
		//String nome=" X= "+x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " volta inicio iv.txt";


		
		System.out.println("\nPolitica:");
	//	Arquivo arquivoSaida =new Arquivo(nome, Arquivo.ESCRITA);
		//lao.print(arquivoSaida);
	//	lao2.print(arquivoSaida,x,y);
		//System.out.println("Quantidade Total de Estados: " + gW.sizeEstados());
		System.out.println("Quantidade Total de Estados: " + p.sizeEstados());
		//arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + gW.sizeEstados());
		//arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + p.sizeEstados());
		
		//System.out.println("\nTempo de execucao: " + cT.getTempo() + " ms");
		//arquivoSaida.escreveArquivo("Tempo de execucao: " + cT.getTempo() + " ms");
		System.out.println("\nTempo de execucao: " + averageTime + " ms");
		System.out.println("\nTempo de execucao2: " + averageTime2 + " ms");
		//arquivoSaida.escreveArquivo("Tempo de execucao: " + averageTime + " ms");
		
	//	arquivoEntrada.fechaArquivo();
		//arquivoSaida.fechaArquivo();
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

