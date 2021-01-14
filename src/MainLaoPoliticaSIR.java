import Problem.Arquivo;
import Problem.Description.Problem;
import Problem.criaProblemaIgorpositivo3mudcost;
import Problem.criaProblemaSIRvariasMeta;
class MainLaoPoliticaSIR
{	
	private static final int SIZE = 1;
    
	public static void main_ORIGINAL(String args[]) throws Exception {
		//if(args.length != 1)
		//{
		//	System.out.println("Um parametro eh necessario.");
		//	System.out.println("Ex.: Main [arquivo de entrada]");
		//	return;
		//}
		Arquivo arquivoEntrada = null;
		 //arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		//arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
	//	arquivoEntrada = new Arquivo("river_3x3.net", Arquivo.LEITURA);

		//Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);
		
		Cronometro cT = new Cronometro();	
		//Problem gW = null;

		System.out.println();
		
		//LAO lao = null;
		//LAO lao2= null;
		LAOtrue2SIR lao2= null;
		double averageTime = 0;
		Problem p = new Problem(1);;
		int x=3;
		int y=3;
		for(int i = 0; i < SIZE; i++)
		{

			//arquivoEntrada = new Arquivo(, Arquivo.LEITURA);
			//LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
			//criaProblemaIgorpositivo2 c2 = new criaProblemaIgorpositivo2(3,3,0.8,p);
		 //	criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(x,y,0.8,p);//meta 0 resto 1 usando atualmente
		 // criaProblemaIgorpositivo3mudcost c = new criaProblemaIgorpositivo3mudcost(x,y,0.8,p,1.0,1,0.99);//meta 0 resto 1
			criaProblemaSIRvariasMeta c = new criaProblemaSIRvariasMeta(10,0,0,0.25,0.75,1,4, p,1.5);
		//criaProblemaIgorVersãoVoltaInicio c = new criaProblemaIgorVersãoVoltaInicio(x,y,0.8,p,1.0,1,0.99);
		//	criaProblemaIgorVersãoVoltaInicio22 c = new criaProblemaIgorVersãoVoltaInicio22(x,y,0.8,p,1.0,1,0.99);
		//	criaProblemaIgorVersãoVoltaInicio3 c = new criaProblemaIgorVersãoVoltaInicio3(x,y,0.8,p,1.0,1,0.99);
		//	criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(7,15,0.8,p);//meta 0 resto -1
		//	criaProblemaIgorNegativonovo c = new criaProblemaIgorNegativonovo(7,10,0.8,p);//meta -1 resto 0
		//	cenarioDenis c = new cenarioDenis(p);
			System.out.println("Lendo Arquivo... " );

			//gW = lGW.executa(-1,0);

			System.out.println("Leitura Concluida!");


			//lao2 = new LAO(p,0,0);
			p.setDiscount(1);
			cT.start();



			p.setTamanhoPasso(1/(1+Math.abs(0.0)));
			p.setK(0.0);
			//lao2 = new LAOtrue2SIR(p,1,0,x,y,0,1);//iv
			//lao2 = new LAOtrue2SIR(p,1,0,x,y);//com determinização
			 lao2 = new LAOtrue2SIR(p,1,0);//h=0
			//lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//lao2=new LAO2(p,0,-1);
			//System.out.println("\nExecutando LAO*...");

			//cT.start();
			//gW.setDiscount(0.99);
			p.setDiscount(1.0);
			//lao.executa(LAO.VALUE);
			p.setTamanhoPasso(1/(1+Math.abs(0.0)));
			//p.setTamanhoPasso(0.5);
			p.setK(0.9);

		//	lao2.executaLaoPolitica(LAO.VALUE);
            lao2.executaLaoVI(LAO.VALUE);
		//	p.setK(0.990);se for executar LAO2
			//lao2.executa2(LAO.VALUE);

			cT.stop();

			averageTime += cT.getTempo();
			
			System.out.println("\nExecucao " + (i+1) + " de " + SIZE);
			System.out.println("Tempo medio " + averageTime/(i+1));

//			arquivoEntrada.fechaArquivo();
		}
		averageTime /= SIZE;

		System.out.println("\nPolitica:");
		Arquivo arquivoSaida =new Arquivo("abc.txt", Arquivo.ESCRITA);
		//lao.print(arquivoSaida);
		lao2.printSIR2(arquivoSaida,11,15);
		//System.out.println("Quantidade Total de Estados: " + gW.sizeEstados());
		System.out.println("Quantidade Total de Estados: " + p.sizeEstados());
		//arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + gW.sizeEstados());
		arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + p.sizeEstados());

		//System.out.println("\nTempo de execucao: " + cT.getTempo() + " ms");
		//arquivoSaida.escreveArquivo("Tempo de execucao: " + cT.getTempo() + " ms");
		System.out.println("\nTempo de execucao: " + averageTime + " ms");
		arquivoSaida.escreveArquivo("Tempo de execucao: " + averageTime + " ms");

		//arquivoEntrada.fechaArquivo();
		arquivoSaida.fechaArquivo();
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
	public static void main(String args[]) throws Exception {
		//System.out.println("ddd".hashCode());
		main_ORIGINAL(args);
		//main_IV
		//main_IV(args);
		return;
	}
}

