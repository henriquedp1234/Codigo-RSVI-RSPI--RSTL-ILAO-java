import Problem.Description.*;
import Problem.*;

import java.util.*;

class Mainnew
{
	//private static final int SIZE = 13;
	//private static final int SIZE = 5;
	public static void main_ORIGINAL(String args[])
	{
		//if(args.length != 1)
		//{
		// System.out.println("Um parametro eh necessario.");
		// System.out.println("Ex.: Main [arquivo de entrada]");
		// return;
		//}
		// Arquivo arquivoEntrada = null;
		//arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		//arquivoEntrada = new Arquivo("river_3x31.net", Arquivo.LEITURA);
		// arquivoEntrada = new Arquivo("river_3x3.net", Arquivo.LEITURA);

		//Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);

		Cronometro cT = new Cronometro();
		Cronometro cT2 = new Cronometro();
		Cronometro cT3 = new Cronometro();
		//Problem gW = null;

		System.out.println();

		//LAO lao = null;
		//LAO lao2= null;
		double ç[]={-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,0,0.9,0.8,0.7,0.6,0.5,0.4};//resto
	//	double ç[]={0.5,0.8,0.9,-0.5 ,-0.8,-0.9,0.99,-0.99};//resto
		//double ç[]={0.0, 0.3, 0.5,0.9,0.99};//hmean
		//double ç[]={0.8};//resto
		int SIZE = ç.length;

			for (int i = 0; i < SIZE; i++) {
				Arquivo arquivoEntrada = null;
				LAO2 lao2 = null;
				//LAOtrue2 lao2= null;
				double averageTime = 0;
				double averageTime2 = 0;
				double averageTime3 = 0;
				Problem p = new Problem(1);

				int x = 13;
				int y = 10;


				//arquivoEntrada = new Arquivo(, Arquivo.LEITURA);
				//LeGridWorld lGW = new LeGridWorld(arquivoEntrada);
				//criaProblemaIgorpositivo2 c2 = new criaProblemaIgorpositivo2(3,3,0.8,p);
				// criaProblemaIgorpositivo3 c = new criaProblemaIgorpositivo3(x,y,0.8,p);//meta 0 resto 1 usando atualmente
				criaProblemaIgorpositivo3mudcost c = new criaProblemaIgorpositivo3mudcost(x, y, 0.8, p, 1.0, 1, 0.99);//meta 0 resto 1
				//criaProblemaIgorVersãoVoltaInicio c = new criaProblemaIgorVersãoVoltaInicio(x,y,0.8,p,1.0,1,0.99);
				//criaProblemaIgorVersãoVoltaInicio22 c = new criaProblemaIgorVersãoVoltaInicio22(x,y,0.8,p,1.0,1,0.99);
				// criaProblemaIgorVersãoVoltaInicio3 c = new criaProblemaIgorVersãoVoltaInicio3(x,y,0.8,p,1.0,1,0.99);
				// criaProblemaIgorNegativo4 c = new criaProblemaIgorNegativo4(7,15,0.8,p);//meta 0 resto -1
				// criaProblemaIgorNegativonovo c = new criaProblemaIgorNegativonovo(7,10,0.8,p);//meta -1 resto 0
				System.out.println("Lendo Arquivo... ");

				//gW = lGW.executa(-1,0);

				System.out.println("Leitura Concluida!");
				System.out.println("iteração" + i);


				//lao2 = new LAO(p,0,0);
				p.setDiscount(1);


				//p.setTamanhoPasso(1/(1+Math.abs(0.0)));
				//p.setK(0.0);
				//lao2 = new LAOtrue2(p,1,0,x,y,0);//iv
				//lao2 = new LAOtrue2(p,1,0,x,y);//com determinização
				//lao2 = new LAOtrue2(p,1,0);//h=0
				cT3.start();
				cT2.start();
				//lao2 = new LAO2(p,1,0);//h=0
				//p.setTamanhoPasso(1/(1+Math.abs(0.0)));
				//p.setK(0.0);
				//lao2 = new LAO2(p,1,0,x,y,0);//iv
				//lao2 = new LAO2(p,1,0,x,y);//com determinização
				lao2 = new LAO2(p, 1, 0);//h=0

				cT2.stop();
				//lao2 = new LAO2(p,0,-1); // se usar criaProblemaIgorNegativo4 usar esse!!!!!!!!!!!!!!!!!!!!!!!!!!!
				//lao2=new LAO2(p,0,-1);
				//System.out.println("\nExecutando LAO*...");

				//cT.start();
				//gW.setDiscount(0.99);
				p.setDiscount(1);
				//lao.executa(LAO.VALUE);
				p.setTamanhoPasso(1 / (1 + Math.abs(ç[i])));
				//p.setTamanhoPasso(0.5);
				p.setK(ç[i]);
				//lao2.executa2(LAO.VALUE);
				// p.setK(0.990);se for executar LAO2
				cT.start();

				lao2.executa(LAO.VALUE);

				cT.stop();
				cT3.stop();
				averageTime += cT.getTempo();
				averageTime2 += cT2.getTempo();
				averageTime3 += cT3.getTempo();

				System.out.println("\nExecucao " + (i + 1) + " de " + SIZE);
				System.out.println("Tempo medio apenas algoritmo " + averageTime);
				System.out.println("Tempo medio apenas heuristica " + averageTime2);
				System.out.println("Tempo medio apenas total " + averageTime3);

//       arquivoEntrada.fechaArquivo();

				String nome = " X= " + x + " Y=" + y + " k= " + p.getK() + " desconto= " + p.getDiscount() + " passo= " + p.getTamanhoPasso() + " resultado lao h=h02222 Rio.txt";

				//averageTime /= SIZE;

				System.out.println("\nPolitica:");
				Arquivo arquivoSaida = new Arquivo(nome, Arquivo.ESCRITA);
				//lao.print(arquivoSaida);
				lao2.print22(arquivoSaida, x, y);
				//System.out.println("Quantidade Total de Estados: " + gW.sizeEstados());
				System.out.println("Quantidade Total de Estados: " + p.sizeEstados());
				//arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + gW.sizeEstados());
				arquivoSaida.escreveArquivo("Quantidade Total de Estados: " + p.sizeEstados());

				//System.out.println("\nTempo de execucao: " + cT.getTempo() + " ms");
				//arquivoSaida.escreveArquivo("Tempo de execucao: " + cT.getTempo() + " ms");
				System.out.println("\nTempo de execucao: " + averageTime + " ms");
				arquivoSaida.escreveArquivo("Tempo de execucao: " + averageTime + " ms");

				System.out.println("qtdeValueIteration " + lao2.qtdeValueIteration);
				arquivoSaida.escreveArquivo("qtdeValueIteration " + lao2.qtdeValueIteration);
				System.out.println("qtdeILAO1 " + lao2.qtdeILAO);
				arquivoSaida.escreveArquivo("qtdeILAO1 " + lao2.qtdeILAO);
				System.out.println("qtdeILAO2 " + lao2.qtdeILAO2);
				arquivoSaida.escreveArquivo("qtdeILAO2 " + lao2.qtdeILAO2);
				System.out.println("qtdedet1 " + lao2.qtdedet);
				arquivoSaida.escreveArquivo("qtdedet1 " + lao2.qtdedet);
				System.out.println("qtdedet 2" + lao2.qtdedet2);
				arquivoSaida.escreveArquivo("qtdedet 2" + lao2.qtdedet2);

				System.out.println("qtdeValueIteration1 " + lao2.qtdeValueIteration);
				arquivoSaida.escreveArquivo("qtdeValueIteration1 " + lao2.qtdeValueIteration);

				System.out.println("qtdeValueIteration2 " + lao2.qtdeValueIteration2);
				arquivoSaida.escreveArquivo("qtdeValueIteration2 " + lao2.qtdeValueIteration2);
				System.out.println("convergido " + lao2.convergido);
				arquivoSaida.escreveArquivo("convergido " + lao2.convergido);
				System.out.println("media de tempo IV ou DET " + lao2.averageTime);
				arquivoSaida.escreveArquivo("media de tempo IV ou DET " + lao2.averageTime);


				System.out.println("Tempo medio apenas algoritmo " + averageTime);
				System.out.println("Tempo medio apenas heuristica " + averageTime2);
				System.out.println("Tempo medio apenas total " + averageTime3);
//    arquivoEntrada.fechaArquivo();
				arquivoSaida.fechaArquivo();
			}

	}


	/*public static void main_IV(String args[]) {

       //Arquivo arquivoEntrada = new Arquivo(args[0], Arquivo.LEITURA);
       Arquivo arquivoEntrada = null;
    // Arquivo arquivoSaida = new Arquivo(args[0].substring(0, args[0].length()-3) + "txt", Arquivo.ESCRITA);

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
