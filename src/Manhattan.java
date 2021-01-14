import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.Description.*;

import java.util.*;

public class Manhattan extends Heuristica {
	public Manhattan(Map<Integer, Estado> e) {
		estadosFinais = e;
	}

	// Heuristica de Manhattan para o estado meta mais proximo
/*	public double calcula(Estado e)
	{
		if(e.isGoal()) return 0;

		int x0 = e.getX();
		int y0 = e.getY();
		int vX = Math.abs(e.getVX());
		int vY = Math.abs(e.getVY());
		if(vX == 0) vX = 1;
		if(vY == 0) vY = 1;
		
		double answer = Double.POSITIVE_INFINITY;
		
		Set<Integer> keys = estadosFinais.keySet();
		for(Integer key : keys)
		{
			int x1 = estadosFinais.get(key).getX();
			int y1 = estadosFinais.get(key).getY();
			
			double aux = Math.abs(x0-x1)/vX + Math.abs(y0-y1)/vY;
			
			answer = Math.min(answer, aux);
		}
		
		return answer;
	}*/
	public double calcula(Estado e) {
		int size = e.actionSize();

		for (int i = 0; i < size; i++) {//itera por todas acoes s
			Acao a = e.getAction(i);//pega ação
			LinkedList<Transicao> t = a.getTransitions(); //pega transição
			double somat=0;
			for (Iterator<Transicao> it = t.iterator(); it.hasNext(); ) //itera por todas transições estado s
			{
				Transicao trans = it.next();
				Estado s =trans.getState();
				int x0 = s.getX();
				int y0 = s.getY();
				int vX = Math.abs(s.getVX());
				int vY = Math.abs(s.getVY());
				if (vX == 0) vX = 1;
				if (vY == 0) vY = 1;
				if(trans.getState().isGoal()==true) {//caso seja meta, saia do laço e some apenas a outra transição.

					break;
				}
				double answer = Double.POSITIVE_INFINITY;
				Set<Integer> keys = estadosFinais.keySet();
				for(Integer key : keys)
				{
					int x1 = estadosFinais.get(key).getX();
					int y1 = estadosFinais.get(key).getY();

					double aux = Math.abs(x0-x1)/vX + Math.abs(y0-y1)/vY;

					answer = Math.min(answer, aux)*trans.getProbA();

				}

				somat+=answer;





			}
			a.setQ(somat);

		}
		return e.getCost();
	}

}