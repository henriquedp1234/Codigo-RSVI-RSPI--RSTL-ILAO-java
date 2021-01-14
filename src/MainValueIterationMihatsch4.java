import Problem.Arquivo;
import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.criaProblemaIgorpositivo3mudcost;

import java.util.HashMap;

class MainValueIterationMihatsch4
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
		
	//	Cronometro cT = new Cronometro();
		//Problem gW = null;

		System.out.println();
		
		//LAO lao = null;
		//LAO lao2= null;
		LAO2 lao2= null;
		double averageTime = 0;
		double averageTime2 = 0;
		Problem p = new Problem(1);;
		int x=50;
		int y=100;

		for(int i = 0; i < SIZE; i++)
		{

			//arquivoEntrada = new Arquivo(, Arquivo.LEITURA);
			//LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
			//criaProblemaIgorpositivo2 c = new criaProblemaIgorpositivo2(3,3,0.8,p);//resto 0
		//	criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(x,y,0.8,p);//meta 0 resto 1			//
			//criaProblemaIgorpositivo4 c = new criaProblemaIgorpositivo4(x,y,0.8,p);
			 criaProblemaIgorpositivo3mudcost c = new criaProblemaIgorpositivo3mudcost(x,y,0.8,p,1,1,0.99);//meta 0 resto 1
		//	criaProblemaIgorVersãoVoltaInicio c= new criaProblemaIgorVersãoVoltaInicio(x,y,0.8,p,1,1,0.99);
			//criaProblemaIgorVersãoVoltaInicio2 c = new criaProblemaIgorVersãoVoltaInicio2(x,y,0.8,p,1.0,1,0.99);
			//criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(x,y,0.8,p);//meta 0 resto -1
		//	criaProblemaIgorNegativonovo c = new criaProblemaIgorNegativonovo(7,10,0.8,p);//meta -1 resto 0
			System.out.println("Lendo Arquivo... " );

			//gW = lGW.executa(-1,0);


			System.out.println("Leitura Concluida!");


			//lao2 = new LAO(p,0,0);
			lao2 = new LAO2(p,1,0,x,y);
			//lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			System.out.println("\nExecutando LAO*...");

			//cT2.start();
		//	lao2.valueIterationHeuristic2(0.00000000000001,x,y);//apenas quando usa heuristica.
			// p.setK(1.0);
			//cT2.stop();

            Cronometro cT2 = new Cronometro();
			Cronometro Ct = new Cronometro();

			p.setK(-0.9);
			p.setTamanhoPasso(1/(1+Math.abs(0.9)));
double desconto=1;
			p.setDiscount(desconto);

		//	lao2.iterterationValue3(0.00000000000001,x,y);//apenas quando usa heuristica.
			Ct.start();
			lao2.iterterationValue(0.00000000000001,x,y);
			Ct.stop();
			String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " p2ffdafdfdfdfdfds.txt";
			String Diretorios = "desconto= " + p.getDiscount() + "/";
			HashMap<Estado,Acao> politica=new HashMap<Estado,Acao> ();
			lao2.imprimedireçãoQ4(x, y, nome, Diretorios,politica);
		/*	for (Iterator<Integer> it2 = p.getEstados().keySet().iterator(); it2.hasNext(); ) {//percorre todos os estados
				Integer k = it2.next();
				Estado estadoCorrente = p.getEstados().get(k);

				politica.put(estadoCorrente,estadoCorrente.getAction(2));
			}*/

			//cT2.start();
		/*	p.setK(0.99);
			p.setTamanhoPasso(1/(1+Math.abs(0.99)));
			p.setDiscount(desconto);
			lao2.policyIteration(politica,x,y);
			//lao2.policyIterationBelman(politica,10,7);
			// p.setK(1.0);
		//	cT2.stop();*/
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




			
			averageTime += Ct.getTempo();
			averageTime2 += cT2.getTempo();
			
			System.out.println("\nExecucao " + (i+1) + " de " + SIZE);
			System.out.println("Tempo medio " + averageTime/(i+1));
//			arquivoEntrada.fechaArquivo();
		}
		//String nome=" X= "+x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " volta inicio iv.txt";

		averageTime /= SIZE;
		
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
		
	//	Cronometro cT = new Cronometro();
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

