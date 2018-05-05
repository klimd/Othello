import java.util.List;

public class SomethingOriginal extends OthelloPlayer{

	private int _depth = 0;
	
	public SomethingOriginal(int depth)
	{
		_depth = depth;
	}
	
	@Override
	public OthelloMove getMove(OthelloState state) {
		
		//get current player
		int player = state.nextPlayerToMove;
		
		// generate the list of moves for the current player
        List<OthelloMove> moves = state.generateMoves(player);                
        
        if(moves.size() > 0)
        {
        	int totalMinMax = -1000000000;
            OthelloMove nextMove = null;
            
        	//for each of the possible moves find the MinMax
            for(OthelloMove move : moves)
            {
            	int stateMinMax = getMinMax(true, state, _depth);
            	//int stateMinMax = alphaBeta(true, -10000000, 10000000, state, _depth);
            	if(stateMinMax > totalMinMax)
        		{
        			totalMinMax = stateMinMax;
        			nextMove = move;
        		}            	
            }            
            return nextMove;
        }           
		
     // If there are no possible moves, just return "pass":
        return null;
	}
	//I implemented the pseucode for Alpha-beta prunning from Wikipedia
	//https://en.wikipedia.org/wiki/Alpha-beta_pruning
	private int alphaBeta(boolean maximizer, int alpha, int beta, OthelloState state, int depth)
	{
		if(depth == 0  || state.gameOver())
			return state.score();
		
		if(maximizer)
		{
			int v = -1000000;
			List<OthelloMove> moves = state.generateMoves(); 
			for(OthelloMove move : moves)
			{
				OthelloState tempState = state.applyMoveCloning(move);
	        	v = max(v, alphaBeta(false, alpha, beta, tempState, depth-1));
	        	alpha = max(alpha, v);
	        	
	        	if(beta <= alpha)
	        		break;
			}
			return v;	
		}
		else
		{
			int v = 1000000;
			List<OthelloMove> moves = state.generateMoves(); 
			for(OthelloMove move : moves)
			{
				OthelloState tempState = state.applyMoveCloning(move);
	        	v = min(v, alphaBeta(true, alpha, beta, tempState, depth-1));
	        	beta = min(beta, v);
	        	
	        	if(beta <= alpha)
	        		break;
			}
			return v;	
		}
	}
	private int min(int a, int b)
	{
		if(a < b)
			return a;
		return b;
	}
	private int max(int a, int b)
	{
		if(a > b)
			return a;
		return b;
	}
	//I implemented the pseucode for MinMax from Wikipedia
	//link: https://en.wikipedia.org/wiki/Minimax
	private int getMinMax(boolean maximizer, OthelloState state, int depth)
	{
		if(depth == 0  || state.gameOver())
			return state.score();
		
		if(maximizer)
		{
			int bestValue = -1000000;
			List<OthelloMove> moves = state.generateMoves(); 
			for(OthelloMove move : moves)
			{
				OthelloState tempState = state.applyMoveCloning(move);
	        	int tempScore = getMinMax(false, tempState, depth-1);
	        	if(tempScore > bestValue)
	        		bestValue = tempScore;	        	
			}
			return bestValue;			
		}
		else
		{
			int bestValue = 1000000;
			List<OthelloMove> moves = state.generateMoves(); 
			for(OthelloMove move : moves)
			{
				OthelloState tempState = state.applyMoveCloning(move);
	        	int tempScore = getMinMax(true, tempState, depth-1);
	        	if(tempScore < bestValue)
	        		bestValue = tempScore;	        	
			}
			return bestValue;
		}
	}
			


}
