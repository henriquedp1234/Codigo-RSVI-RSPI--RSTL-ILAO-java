import Problem.Arquivo;
import Problem.Arquivo2;
import Problem.Description.Problem;
import Problem.criaProblemaSIRvariasMeta;

class MainSIRComTempo
{	
	private static final int SIZE = 1;
    
	public static void main_ORIGINAL(String args[]) {
		//if(args.length != 1)
		//{
		//	System.out.println("Um parametro eh necessario.");
		//	System.out.println("Ex.: Main [arquivo de entrada]");
		//	return;
		//}
		//Arquivo arquivoEntrada = null;
		//arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		//arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		//arquivoEntrada = new Arquivo("river_3x3.net", Arquivo.LEITURA);

		//Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);

	//	Cronometro cT = new Cronometro();
	//	Cronometro cTh = new Cronometro();
	//	Cronometro cTT = new Cronometro();
		//Problem gW = null;

		//System.out.println();

		//LAO lao = null;
		//LAO lao2= null;
		LAOSIRT lao2 = null;
		//double averageTime = 0;
		//	double k[] = {0.0,-0.9,-0.5,0.8,-0.8,0.5, 0.9,};
		//	int costv[] = {1, 1, 1,1, 1, 1,1, 1, 1};
		//	int costinf[] = {4,4,4,4,4,4,4,4,4,4,4,4};
		//	double bet[] =    {0.75,0.8,0.25,0.2,0.25};
		//	double lambda[] = {0.25,0.4,0.25,0.25,0.75};

		int costinf[] = { 4,4,4,4,4,4,4,4,4};
		int costv[] = {1,1,1,1,1,1,1,1,1};
		//  int costinf[] = { 4,4,4,4,4,4,4,4,4};

		//int costinf[] = {1, 4, 1};
		//   double bet[] = {0.25};
		// double lambda[] = {0.25};
		// int costv[] = {10, 10, 10,10, 10, 10,10, 10, 10};
		//int costinf[] = {40,40,40,40,40,40,40,40,40,40,40,40};
		double bet[] =    {0.2,0.75,0.8,0.25,0.25};
		double lambda[] = {0.25,0.25,0.4,0.25,0.75};
		double k[] = {0.0,0.8,-0.8,-0.5,0.5, 0.99,-0.99};
		Problem p = new Problem(1);
		for (int l = 0; l < 7; l++) {
			for (int j = 0; j < 5; j++) {
				System.out.println("rodando:" + "beta= "+ bet[j] +" lambda= " + lambda[j] + " k="+ k[l]);
				for (int i = 0; i < SIZE; i++) {
					Cronometro cT = new Cronometro();
					Cronometro cTh = new Cronometro();
					Cronometro cTT = new Cronometro();
					double averagetimeTotale=0;
					double averageTime = 0;
					//	for (int l = 0; l < 7; l++) {
					//		for (int j = 0; j < 1; j++) {
					//arquivoEntrada = new Arquivo(, Arquivo.LEITURA);
					//LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
					//criaProblemaIgorpositivo2 c2 = new criaProblemaIgorpositivo2(3,3,0.8,p);resto 0
					//criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(3,3,0.8,p);//meta 0 resto 1
					//	criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(7,15,0.8,p);//meta 0 resto -1


					//	criaProblemaSIRvariasMeta c = new criaProblemaSIRvariasMeta(100, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p, 1.5);

							int tam = 30;
							double exp = 1.5;
							// criaProblemaSIR c = new criaProblemaSIR(tam, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p, exp[q]);
					criaProblemaSIRvariasMeta c = new criaProblemaSIRvariasMeta(tam, 0, 0, bet[j], lambda[j], costv[j], costinf[j], p, exp); //problema usando !!
					System.out.println("Lendo Arquivo... ");
					//gW = lGW.executa(-1,0);

					System.out.println("Leitura Concluida!");
					int x=0;
					int y=0;
					//lao2 = new LAOSIRT(p,1,0);//h=0
					//p.setTamanhoPasso(1/(1+Math.abs(0.0)));
					//p.setK(0.0);
					cTT.start();
					cTh.start();
					p.setDiscount(1.0);
				//	p.setTamanhoPasso(1 / (1 + Math.abs(0.0)));
				//	p.setK(0.0);
				//	lao2 = new LAOSIRT(p,1,0,x,y,0);//iv
				lao2 = new LAOSIRT(p,1,0);
					//lao2 = new LAOSIRT(p,1,0,x,y);//com determinização
					cTh.stop();
					//lao2 = new LAOSIRT(p,1,0,x,y);//com determinização
					//lao2 = new LAOSIRT(p,1,0);//h=0
					//lao2 = new LAOSIRT(p,0,0);
					//lao2 = new LAOSIRT(p, 0, 1);
					//lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!

					System.out.println("\nExecutando LAO*...");
					//	cT.start();
					cT.start();
					//gW.setDiscount(0.99);

					p.setDiscount(1);
					p.setK(k[l]);
					//lao.executa(LAO.VALUE);
					lao2.executa(LAO.VALUE);


					cT.stop();
					cTT.stop();
					double averageTime2=0;
					averageTime += cT.getTempo();//alg
					averageTime2 += cTh.getTempo();//heur
					averagetimeTotale+=cTT.getTempo();//total

					//	System.out.println("\nExecucao " + (i + 1) + " de " + SIZE);
					System.out.println("Tempo medio " + averageTime / (i + 1));//
					//	arquivoEntrada.fechaArquivo();


					averageTime /= SIZE;

					System.out.println("\nPolitica:");
					String DirName = "S= " + tam + "exp= " + exp + "SIRCOMTEMPOvv22";
					Arquivo2 arquivoSaida = new Arquivo2("H=IV SIR" + p.getK() + "lambda" + lambda[j] + "beta" + bet[j] + ".txt", Arquivo.ESCRITA,DirName);
					Arquivo2 arquivoSaida2t = new Arquivo2("H=IV Imprime espaço SIR" + p.getK() + "lambda" + lambda[j] + "beta" + bet[j] + ".txt", Arquivo.ESCRITA,DirName);
					Arquivo2 arquivoSaidatempo = new Arquivo2("H=IV Tempo SIR N=100 " + p.getK() + "lambda" + lambda[j] + "beta" + bet[j] + ".txt", Arquivo.ESCRITA,DirName);
					//Arquivo arquivoSaida =new Arquivo("SIR.txt",Arquivo.ESCRITA);
					//lao.print(arquivoSaida);


					lao2.printSIR2(arquivoSaida, tam+1, 15,arquivoSaida2t);
					//System.out.println("Quantidade Total de Estados: " + gW.sizeEstados());
					System.out.println("Quantidade Total de Estados: " + p.sizeEstados());
					//arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + gW.sizeEstados());
					arquivoSaidatempo.escreveArquivo("Quantidade Total de Estados: " + p.sizeEstados());

					//System.out.println("\nTempo de execucao: " + cT.getTempo() + " ms");
					//arquivoSaida.escreveArquivo("Tempo de execucao: " + cT.getTempo() + " ms");
					System.out.println("\nTempo de execucao: " + averageTime + " ms");
					arquivoSaidatempo.escreveArquivo("Tempo de execucao only algoritmo: " + averageTime + " ms");
					System.out.println("\nTempo de execucao only heuristica: " + averageTime2 + " ms");
					arquivoSaidatempo.escreveArquivo("Tempo de execucao only heuristica: " + averageTime2 + " ms");
					System.out.println("\n\"Tempo de execucao TOTAL: \" "+ averagetimeTotale + " ms");
					arquivoSaidatempo.escreveArquivo("Tempo de execucao TOTAL: " + averagetimeTotale + " ms");

					//arquivoEntrada.fechaArquivo();
					arquivoSaidatempo.fechaArquivo();
				}
			}
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
