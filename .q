[1mdiff --git a/bin/adress/model/Graph.class b/bin/adress/model/Graph.class[m
[1mindex 1acc278..83c286f 100644[m
Binary files a/bin/adress/model/Graph.class and b/bin/adress/model/Graph.class differ
[1mdiff --git a/bin/adress/view/LayoutController$1.class b/bin/adress/view/LayoutController$1.class[m
[1mindex d52776f..3516203 100644[m
Binary files a/bin/adress/view/LayoutController$1.class and b/bin/adress/view/LayoutController$1.class differ
[1mdiff --git a/bin/adress/view/LayoutController.class b/bin/adress/view/LayoutController.class[m
[1mindex 8972508..461524a 100644[m
Binary files a/bin/adress/view/LayoutController.class and b/bin/adress/view/LayoutController.class differ
[1mdiff --git a/src/adress/model/Graph.java b/src/adress/model/Graph.java[m
[1mindex 8479bc6..7b2ab01 100644[m
[1m--- a/src/adress/model/Graph.java[m
[1m+++ b/src/adress/model/Graph.java[m
[36m@@ -29,8 +29,6 @@[m [mpublic class Graph {[m
 		alpha = distrExp;[m
 		minimalNodeDegree = xMin;[m
 		ers = Ers;[m
[31m-		System.out.println("set: size=" + size + " numOB=" + numberOfBlocks + " sOB=" + sizeOfBlock + " alpha=" + alpha[m
[31m-				+ " Ers=" + ers);[m
 		graph = new boolean[size][size];[m
 		nodeDegrees = new double[size];[m
 		internalLagrangeMultipliers = new double[size];[m
[1mdiff --git a/src/adress/view/LayoutController.java b/src/adress/view/LayoutController.java[m
[1mindex fe7c359..8d22743 100644[m
[1m--- a/src/adress/view/LayoutController.java[m
[1m+++ b/src/adress/view/LayoutController.java[m
[36m@@ -148,8 +148,8 @@[m [mpublic class LayoutController {[m
 	public void initSliders() {[m
 		sizeLabel.setText("Size = " + Integer.toString((int) size.getValue()));[m
 		numberOfBlocksLabel.setText("Blocks = " + Integer.toString((int) numberOfBlocks.getValue()));[m
[31m-		alphaLabel.setText("alpha = " + Integer.toString((int) alpha.getValue()));[m
[31m-		xMinLabel.setText("xMin = " + Integer.toString((int) xMin.getValue()));[m
[32m+[m		[32malphaLabel.setText("α  = " + Integer.toString((int) alpha.getValue()));[m
[32m+[m		[32mxMinLabel.setText("xMin = "  + Integer.toString((int) xMin.getValue()));[m
 		ersLabel.setText("Ers = " + Integer.toString((int) ers.getValue()));[m
 [m
 		size.valueProperty().addListener((observable, oldValue, newValue) -> {[m
