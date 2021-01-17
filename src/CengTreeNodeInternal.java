import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations, if necessary.
        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
        this.type = CengNodeType.Internal;
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions
    public void addKey(Integer index, Integer key) {
        this.keys.add(index,key);
    }

    public void addChild(Integer index, CengTreeNode child) {
        this.children.add(index,child);
    }

    public void changeChild(Integer index, CengTreeNode child) {
        this.children.set(index,child);
    }

    public Integer findChildInd(CengTreeNode child) {
        return this.children.indexOf(child);
    }

    public CengTreeNode getChild(Integer index) {
        return this.children.get(index);
    }


}
