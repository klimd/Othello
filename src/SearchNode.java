import java.util.ArrayList;
import java.util.List;

public class SearchNode {

	OthelloState _state;
	SearchNode _parent;
	List<SearchNode> _children;
	List<OthelloState> _childrenStates;
	OthelloMove _action;
	int _numVisits;
	int _avgScore;
	int _stateScore;
		
	public SearchNode(OthelloState state)
	{
		_state = state;
		_parent = null;
		_children = new ArrayList<SearchNode>();
		_action = null;
		_numVisits = 0;
		_avgScore = 0;
		_stateScore = state.score();
	}
	public void addChildren(SearchNode child)
	{
		_children.add(child);
	}
	public List<SearchNode> getChildren()
	{
		return _children;
	}
	public List<OthelloState> getChildrenStates()
	{
		return _childrenStates;
	}	
	public SearchNode findMinChild()
	{
		int min = _children.get(0).getAvgScore();
		SearchNode minNode = _children.get(0);
		for(SearchNode child : _children)
		{
			if(child.getAvgScore() < min)
			{
				min = child.getAvgScore();
				minNode = child;
			}
			
		}
		return minNode;
	}
	public SearchNode findMaxChild()
	{
		int max = _children.get(0).getAvgScore();
		SearchNode maxNode = _children.get(0);
		for(SearchNode child : _children)
		{
			if(child.getAvgScore() > max)
			{
				max = child.getAvgScore();
				maxNode = child;
			}
		}
		return maxNode;
	}

	//avgScore 
	public void setAvgScore(int score)
	{
		_avgScore = score;
	}
	public int getAvgScore()
	{
		return _avgScore;
	}
	//action
	public void setAction(OthelloMove action)
	{
		_action = action;
	}
	public OthelloMove getAction()
	{
		return _action;
	}
	//visit
	public void addVisit()
	{
		_numVisits++;
	}
	//parent
	public SearchNode getParent()
	{
		return _parent;
	}
	public void setParent(SearchNode parent)
	{
		_parent = parent;
	}
	//state
	public OthelloState getState()
	{
		return _state;
	}	
}
