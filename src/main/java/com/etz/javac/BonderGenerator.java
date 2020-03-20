package com.etz.javac;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

public final class BonderGenerator {
    final TreeMaker treeMaker;
    final Names names;
    final Context context;

    public BonderGenerator(JavacTask javacTask) {
        this.context = ((BasicJavacTask) javacTask).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    void addTo(CompilationUnitTree cUnit){
        cUnit.getTypeDecls().stream().filter(t -> t instanceof JCTree.JCClassDecl)
                .forEach(classDecl->{
                    JCTree.JCClassDecl classDecl1 = (JCTree.JCClassDecl) classDecl;
                    System.out.println(classDecl1.defs);

                    classDecl1.defs.stream()
                            .filter(m->m instanceof JCTree.JCMethodDecl).forEach(d->{
                        if(d.type==null){
                            JCTree.JCMethodDecl md= (JCTree.JCMethodDecl) d;

                            //md.body=treeMaker.Block(0,prepend);
                            md.body.stats=md.body.stats.prependList(addVar());
                        }

                    });

                });

        System.out.println(">>>>");
        System.out.println(cUnit);
    }

    List<JCTree.JCStatement> addVar(){
        JCTree.JCNewClass jcNewClass = treeMaker.NewClass(null, null, javaTypeExpr("com.etz.javac.Bonder"),
                List.nil(), null);


        JCTree.JCVariableDecl bonder = treeMaker.VarDef(treeMaker.Modifiers(0L),
                names.fromString("bonder"),
                javaTypeExpr("com.etz.javac.Bonder"),
                null);
        JCTree.JCAssign assign = treeMaker.Assign(treeMaker.Ident(names.fromString("bonder")), jcNewClass);

        return List.of(bonder,treeMaker.Exec(assign).setType(bonder.type));
    }

    protected JCTree.JCExpression javaTypeExpr(String javaTypeName) {
        return dottedId(javaTypeName, 0);
    }
    protected JCTree.JCExpression dottedId(String dotted, int pos) {
        if (pos >= 0) {
            treeMaker.at(pos);
        }
        String[] idents = dotted.split("\\.");
        JCTree.JCExpression ret = treeMaker.Ident(names.fromString(idents[0]));

        for (int i = 1; i < idents.length; i++) {
            ret = treeMaker.Select(ret, names.fromString(idents[i]));
        }
        return ret;
    }
}
