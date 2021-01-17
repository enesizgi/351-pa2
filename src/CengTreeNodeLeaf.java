import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengVideo> videos;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations
        videos = new ArrayList<CengVideo>();
        this.type = CengNodeType.Leaf;
    }

    // GUI Methods - Do not modify
    public int videoCount()
    {
        return videos.size();
    }
    public Integer videoKeyAtIndex(Integer index)
    {
        if(index >= this.videoCount()) {
            return -1;
        } else {
            CengVideo video = this.videos.get(index);

            return video.getKey();
        }
    }

    // Extra Functions
    public void addVideo(Integer index, CengVideo video) {
        videos.add(index,video);
    }

    public CengVideo videoAtIndex(Integer index) {
        return this.videos.get(index);
    }
}
