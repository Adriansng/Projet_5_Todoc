package com.cleanup.todoc.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Adrian SENEGAS 24/04/2020.
 */
public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository taskDataSource;

    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Task>> currentTasks;
    @Nullable
    private Project currentProject;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void initProject(long projectId){
        if(this.currentProject != null){
            currentProject = projectDataSource.getProject(projectId);
        }
    }

    public void init(){
        if(this.currentTasks != null){
            currentTasks= taskDataSource.getAllTasks();
        }
    }

    // --------------------
    // FOR TASK
    // --------------------

    public LiveData<List<Task>> getTasks(){
        return taskDataSource.getAllTasks();
    }

    public void createTask(Task task){
        executor.execute(() -> taskDataSource.createTask(task));}

    public void deleteTask(long taskId){
        executor.execute(() -> taskDataSource.deleteTask(taskId));}

}
