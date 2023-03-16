package com.mycompany.rts_3;

// Benchmarking class that contains the main system functions
public class benchmark_var {

    boolean closing_time = false;

    public void bench_time() {

        new Thread(new Intern()).start();
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
        }
        //new Thread(new Designer(this)).start();
        Designer d = new Designer(this);
        d.render();
        Thread generatedDesigner = new Thread(d);
        generatedDesigner.start();
        new Thread(new ProjectManager()).start();
        
        
        try {
            Thread.sleep(53000);
            closing_time = true;
        } catch (Exception e) {
        }

    }
}
