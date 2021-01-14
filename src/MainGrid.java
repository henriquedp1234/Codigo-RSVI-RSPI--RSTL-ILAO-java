import Problem.*;
import Problem.Description.Problem;
import Problem.criaProblemaIgorNegativonovo;

class MainGrid
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
		//Arquivo arquivoEntrada = null;
		 //arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		//arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		//arquivoEntrada = new Arquivo("river_3x3.net", Arquivo.LEITURA);

		//Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);
		
		//Cronometro cT = new Cronometro();
		//Problem gW = null;

		//System.out.println();
		
		//LAO lao = null;
		//LAO lao2= null;
		LAOSIRT2 lao2= null;
		double averageTime = 0;
		Problem p = new Problem(1);;
		for(int i = 0; i < SIZE; i++)
		{
			//arquivoEntrada = new Arquivo(, Arquivo.LEITURA);
			//LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
			//criaProblemaIgorpositivo2 c2 = new criaProblemaIgorpositivo2(3,3,0.8,p);resto 0
			//criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(3,3,0.8,p);//meta 0 resto 1
		//	criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(7,15,0.8,p);//meta 0 resto -1
			int[] x = {3};
			int[] y = {4};
			int []metax={9};
			int []metay={9};

			criaProblemaGridWorld c = new criaProblemaGridWorld(10,10,p,x,y,metax,metay,0.99,0,2,1,0,0,0.99);

			System.out.println("Lendo Arquivo... " );
			//gW = lGW.executa(-1,0);

			System.out.println("Leitura Concluida!");


			//lao2 = new LAO(p,0,0);
			lao2 = new LAOSIRT2(p,0,1);
			//lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			System.out.println("\nExecutando LAO*...");
		//	cT.start();
			//cT.start();
			//gW.setDiscount(0.99);
			p.setDiscount(1);
			//lao.executa(LAO.VALUE);
			lao2.executa(LAO.VALUE);
		//	cT.stop();
			
		//	averageTime += cT.getTempo();
			
			System.out.println("\nExecucao " + (i+1) + " de " + SIZE);
			System.out.println("Tempo medio " + averageTime/(i+1));
		//	arquivoEntrada.fechaArquivo();
		}


		averageTime /= SIZE;
		
		System.out.println("\nPolitica:");
		Arquivo arquivoSaida =new Arquivo("abc.txt", Arquivo.ESCRITA);
		//lao.print(arquivoSaida);
		lao2.print2(arquivoSaida,10,10);
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
	public static void main(String args[]) {
		//System.out.println("ddd".hashCode());
		main_ORIGINAL(args);
		//main_IV
		//main_IV(args);
		return;
	}
}
