import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = new CengTreeNodeLeaf(null);

    }

    public void addVideo(CengVideo video)
    {
        // TODO: Insert Video to Tree
        CengTreeNode tmp = findPlace(video);
        addVideoToPlace(tmp,video);

        Integer order = this.root.getOrder();
        if (2 * order < ((CengTreeNodeLeaf) tmp).videoCount()) {
            tmp = copy(tmp);

            while (2*order < ((CengTreeNodeInternal)tmp).keyCount()) {
                tmp = push(tmp);
            }

            if (tmp.getParent() == null) {
                root = tmp;
            }
        }


    }

    public ArrayList<CengTreeNode> searchVideo(Integer key)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        StringBuilder output = new StringBuilder();
        if (root == null) {
            System.out.println(output.append("Could not find ").append(key.toString()).append("."));
            return null;
        }


        ArrayList<CengTreeNode> videos = new ArrayList<>();
        Integer n;
        CengTreeNode tmp = root;
        StringBuilder indent = new StringBuilder();
        videos.add(tmp);
        while (tmp.getType() != CengNodeType.Leaf) {
            n = ((CengTreeNodeInternal) tmp).keyCount();
            output.append(indent).append("<index>").append("\n");
            for (int i = 0; i < n ; i++) {
                output.append(indent).append(((CengTreeNodeInternal) tmp).keyAtIndex(i)).append("\n");
            }
            output.append(indent).append("</index>").append("\n");
            if (key < ((CengTreeNodeInternal) tmp).keyAtIndex(0)) {
                tmp = ((CengTreeNodeInternal) tmp).getChild(0);
            }
            else if (((CengTreeNodeInternal) tmp).keyAtIndex(n - 1) <= key) {
                tmp = ((CengTreeNodeInternal) tmp).getChild(n);
            }
            else {
                for (int i = 0; i <= n - 2; i++) {
                    if (((CengTreeNodeInternal) tmp).keyAtIndex(i) <= key && key < ((CengTreeNodeInternal) tmp).keyAtIndex(i + 1)) {
                        tmp = ((CengTreeNodeInternal) tmp).getChild(i + 1);
                        break;
                    }
                }
            }
            indent.append("\t");
            videos.add(tmp);
        }

        n = ((CengTreeNodeLeaf) tmp).videoCount();
        for (int i = 0; i < n; i++) {
            CengVideo video = ((CengTreeNodeLeaf) tmp).videoAtIndex(i);
            if (video.getKey().equals(key)) {
                output.append(indent).append("<record>").append(video.fullName()).append("</record>").append("\n");
                System.out.print(output);
                return videos;
            }
        }
        System.out.println("Could not find " + key.toString() + ".");
        return null;
    }

    public void printTree()
    {
        // TODO: Print the whole tree to console
        printTreeHelper(root,0);
    }

    // Any extra functions...

    private CengTreeNode findPlace(CengVideo video) {
        Integer n;
        CengTreeNode tempNode = root;
        while (tempNode.getType() != CengNodeType.Leaf) {
            n = ((CengTreeNodeInternal) tempNode).keyCount();

            if (video.getKey() < ((CengTreeNodeInternal) tempNode).keyAtIndex(0)) {
                tempNode = ((CengTreeNodeInternal) tempNode).getChild(0);
            }

            else if (((CengTreeNodeInternal) tempNode).keyAtIndex(n - 1) <= video.getKey()) {
                tempNode = ((CengTreeNodeInternal) tempNode).getChild(n);
            }

            else {
                for (int i = 0; i <= n - 2; i++) {
                    if (((CengTreeNodeInternal) tempNode).keyAtIndex(i) <= video.getKey() && video.getKey() < ((CengTreeNodeInternal) tempNode).keyAtIndex(i + 1)) {
                        tempNode = ((CengTreeNodeInternal) tempNode).getChild(i + 1);
                        break;
                    }
                }
            }
        }
        return tempNode;
    }

    private void addVideoToPlace(CengTreeNode tmp, CengVideo video) {
        int n = ((CengTreeNodeLeaf) tmp).videoCount();
        for (int i = 0; i < n; i++) {
            if (video.getKey() < ((CengTreeNodeLeaf) tmp).videoKeyAtIndex(i)) {
                ((CengTreeNodeLeaf) tmp).addVideo(i, video);
                return;
            }
        }
        ((CengTreeNodeLeaf) tmp).addVideo(n, video);
    }

    private CengTreeNode copy(CengTreeNode tmp) {

        Integer video_count = ((CengTreeNodeLeaf) tmp).videoCount();
        Integer middle_index = video_count / 2;
        Integer middle_key = ((CengTreeNodeLeaf) tmp).videoKeyAtIndex(middle_index);

        CengTreeNode newNode1 = new CengTreeNodeLeaf(null);
        CengTreeNode newNode2 = new CengTreeNodeLeaf(null);

        CengTreeNodeInternal parent_tmp = (CengTreeNodeInternal) tmp.getParent();

        for (int i = 0; i < middle_index; i++) {
            ((CengTreeNodeLeaf) newNode1).addVideo(i, ((CengTreeNodeLeaf) tmp).videoAtIndex(i));
        }

        for (int i = middle_index; i < video_count; i++) {
            ((CengTreeNodeLeaf) newNode2).addVideo(i - middle_index, ((CengTreeNodeLeaf) tmp).videoAtIndex(i));
        }

        if (parent_tmp == null) {
            parent_tmp = new CengTreeNodeInternal(null);
            parent_tmp.addKey(0, middle_key);
            parent_tmp.addChild(0, newNode1);
            parent_tmp.addChild(1, newNode2);
        }
        else {
            Integer childIndex = parent_tmp.findChildInd(tmp);
            parent_tmp.addKey(childIndex, middle_key);
            parent_tmp.changeChild(childIndex, newNode1);
            parent_tmp.addChild(childIndex + 1, newNode2);
        }

        newNode1.setParent(parent_tmp);
        newNode2.setParent(parent_tmp);

        return parent_tmp;

    }

    private CengTreeNode push(CengTreeNode tmp) {
        Integer keyCount = ((CengTreeNodeInternal) tmp).keyCount();
        Integer middle_index = keyCount / 2;
        Integer middle_key = ((CengTreeNodeInternal) tmp).keyAtIndex(middle_index);
        CengTreeNode new_node1 = new CengTreeNodeInternal(null);
        CengTreeNode new_node2 = new CengTreeNodeInternal(null);
        CengTreeNode child;

        CengTreeNodeInternal parent_tmp = (CengTreeNodeInternal) tmp.getParent();
        for (int i = 0; i < middle_index; i++) {

            child = ((CengTreeNodeInternal) tmp).getChild(i);
            child.setParent(new_node1);
            ((CengTreeNodeInternal) new_node1).addChild(i, child);
            ((CengTreeNodeInternal) new_node1).addKey(i, ((CengTreeNodeInternal) tmp).keyAtIndex(i));
        }

        child = ((CengTreeNodeInternal) tmp).getChild(middle_index);
        child.setParent(new_node1);
        ((CengTreeNodeInternal) new_node1).addChild(middle_index, child);
        for (int i = middle_index + 1; i < keyCount; i++) {

            child = ((CengTreeNodeInternal) tmp).getChild(i);
            child.setParent(new_node2);
            ((CengTreeNodeInternal) new_node2).addChild(i - middle_index - 1, child);
            ((CengTreeNodeInternal) new_node2).addKey(i - middle_index - 1, ((CengTreeNodeInternal) tmp).keyAtIndex(i));
        }

        child = ((CengTreeNodeInternal) tmp).getChild(keyCount);
        child.setParent(new_node2);
        ((CengTreeNodeInternal) new_node2).addChild(keyCount - middle_index - 1, child);

        if (parent_tmp == null) {
            parent_tmp = new CengTreeNodeInternal(null);
            parent_tmp.addKey(0, middle_key);
            parent_tmp.addChild(0, new_node1);
            parent_tmp.addChild(1, new_node2);
        }

        else {
            Integer childIndex = parent_tmp.findChildInd(tmp);
            parent_tmp.addKey(childIndex, middle_key);
            parent_tmp.changeChild(childIndex, new_node1);
            parent_tmp.addChild(childIndex + 1, new_node2);
        }

        new_node1.setParent(parent_tmp);
        new_node2.setParent(parent_tmp);

        return parent_tmp;

    }

    private void printTreeHelper(CengTreeNode tempNode, Integer loopCounter) {
        Integer counter;
        String indent = "\t".repeat(loopCounter);

        if (tempNode.getType() == CengNodeType.Leaf) {
            counter = ((CengTreeNodeLeaf) tempNode).videoCount();
            System.out.println(indent + "<data>");

            for (int i = 0; i < counter; i++) {
                System.out.println(indent + "<record>" + ((CengTreeNodeLeaf) tempNode).videoAtIndex(i).fullName() + "</record>");
            }

            System.out.println(indent + "</data>");
            return;
        }

        counter = ((CengTreeNodeInternal) tempNode).keyCount();
        System.out.println(indent + "<index>");

        for (int i = 0; i < counter; i++) {
            System.out.print(indent);
            System.out.println(((CengTreeNodeInternal) tempNode).keyAtIndex(i));
        }

        System.out.println(indent + "</index>");
        for (int i = 0; i < counter + 1; i++) {
            printTreeHelper(((CengTreeNodeInternal) tempNode).getChild(i),loopCounter+1);
        }
    }

}
