import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonTeCarloTreeSearch extends OthelloPlayer {

	@Override
	public OthelloMove getMove(OthelloState state) {
		
		//get current player
		int player = state.nextPlayerToMove;
				
		// generate the list of moves for the current player
		List<OthelloMove> moves = state.generateMoves(player);                
		        
		if(moves.size() > 0)
		{
			return MonteCarloTreeSearch(state, 100);
		}
		return null;
	}
	
	private OthelloMove MonteCarloTreeSearch(OthelloState board, int iterations)
	{
		//create root
		SearchNode root = createNode(board);
		
		for(int i = 0 ; i < iterations ; i++)
		{
			SearchNode node = treePolicy(root);
			if(node != null)
			{
				SearchNode node2 = defaultPolicy(node);
				int node2score = score(node2);
				backup(node, node2score);				
			}			
		}
		
		return bestChild(root).getAction();
	}

	private SearchNode createNode(OthelloState board)
	{
		return new SearchNode(board);
	}
	
	private SearchNode bestChild(SearchNode node)
	{
		OthelloState tempState = node.getState();
				
		if(tempState.nextPlayerToMove == 0)
		{
			return node.findMaxChild();
		}
		else
		{
		
			return node.findMinChild();
		}
	}
	
	private SearchNode treePolicy(SearchNode node)
	{
		List<OthelloMove> moves = node.getState().generateMoves();
		List<OthelloState> childStates = new ArrayList<OthelloState>();
		List<SearchNode> childNodes = new ArrayList<SearchNode>();
		
		if(!moves.isEmpty())
		{
			for(OthelloMove move: moves)
			{
				OthelloState tempState = node.getState().applyMoveCloning(move);
				childStates.add(tempState);
				SearchNode tempNode = new SearchNode(tempState);
				//set parent
				tempNode.setParent(node);
				//set action
				tempNode.setAction(move);
				childNodes.add(tempNode);
			}
		}
		
		if(!childNodes.isEmpty())
		{
			SearchNode newNode = childNodes.get(0);
			childNodes.remove(0);
			node.addChildren(newNode);
			return newNode;
		}
		else if (node.getState().gameOver())
		{
			return node;
		}
		else if (!node.getState().gameOver() && childNodes.isEmpty())
		{
			SearchNode nodetmp;
			Random random = new Random();
			if(random.nextInt(100) < 90)
			{
				nodetmp = bestChild(node);
			}
			else
			{
				int randChild = random.nextInt(childNodes.size());
				nodetmp = childNodes.get(randChild);
			}
			return treePolicy(nodetmp);
		}
	
		return node;
	}
	
	private SearchNode defaultPolicy(SearchNode node)
	{
		OthelloRandomPlayer randomAgent = new OthelloRandomPlayer();
		OthelloState board = node.getState();
		while(!board.gameOver())
		{
			OthelloMove nextMove = randomAgent.getMove(node.getState());
			board = board.applyMoveCloning(nextMove);
		}
		SearchNode tempNode = new SearchNode(board);
		return tempNode;
	}
	
	private int score(SearchNode node2)
	{
		return node2.getState().score();
	}
	
	private void backup(SearchNode node, int score)
	{
		node.addVisit();
		int newScore = ((node._numVisits-1) * node.getAvgScore() + score)/node._numVisits;//  (((n.NumberVisited-1)*n.AverageScore)+score)/n.NumberVisited;
		node.setAvgScore(newScore);
		if(node.getParent() != null)
		{
			backup(node.getParent(), newScore);
		}
	}
}
