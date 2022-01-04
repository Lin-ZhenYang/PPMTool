import React, { Component } from 'react'
import ProjectTask from './ProjectTasks/ProjectTask';

class Backlog extends Component {
    constructor(props){
        super(props);
        this.state = {
            project_tasks_prop: null
        };
    }

    componentDidUpdate(prevProps) {
        if(prevProps.project_tasks_prop !== this.props.project_tasks_prop) {
          this.setState({project_tasks_prop: this.props.project_tasks_prop});
        }
    }
      

    render() {
        const {project_tasks_prop} = this.props;
        var tasks = null;
        let toDoItems = [];
        let inProgressItems = [];
        let doneItems = [];
        if (this.state.project_tasks_prop == null ){
            tasks = null;
        } else {
            tasks = this.state.project_tasks_prop.map((each_project_task) => 
            <ProjectTask key={each_project_task.id} project_task={each_project_task} />);
            for (let i=0; i<tasks.length; i++){
                if (tasks[i].props.project_task.status==="TO_DO"){
                    toDoItems.push(tasks[i]);
                } else if(tasks[i].props.project_task.status==="IN_PROGRESS"){
                    inProgressItems.push(tasks[i]);
                } else if(tasks[i].props.project_task.status==="DONE"){
                    doneItems.push(tasks[i]);
                }
            }
        }
        
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-4">
                    <div className="card text-center mb-2">
                        <div className="card-header bg-secondary text-white">
                        <h3>TO DO</h3>
                        </div>
                    </div>
                    {toDoItems}
                    </div>
                    <div className="col-md-4">
                    <div className="card text-center mb-2">
                        <div className="card-header bg-primary text-white">
                        <h3>In Progress</h3>
                        </div>
                    </div>
                    {inProgressItems}
                    </div>
                    <div className="col-md-4">
                    <div className="card text-center mb-2">
                        <div className="card-header bg-success text-white">
                        <h3>Done</h3>
                        </div>
                    </div>
                    {doneItems}
                    </div>
                </div>
            </div>
        )
    }
}

export default Backlog;
