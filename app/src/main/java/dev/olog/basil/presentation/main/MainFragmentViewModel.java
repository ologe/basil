package dev.olog.basil.presentation.main;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dev.olog.basil.data.RecipeGateway;
import dev.olog.basil.domain.entity.Recipe;
import dev.olog.basil.presentation.model.DisplayableRecipe;
import dev.olog.basil.presentation.model.DisplayableRecipeImage;
import dev.olog.basil.utils.ListUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<List<DisplayableRecipeImage>> recipeListLiveData = new MutableLiveData<>();

    private final MutableLiveData<Integer> currentPositionPublisher = new MutableLiveData<>();

    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final RecipeGateway recipeGateway;

    @Inject MainFragmentViewModel(RecipeGateway recipeGateway) {
        this.recipeGateway = recipeGateway;

        currentPositionPublisher.setValue(0);

        Disposable disposable = recipeGateway.observeAll()
                .subscribeOn(Schedulers.io())
                .map(recipes -> ListUtils.map(recipes, MainFragmentViewModel::toPresentationAsImage))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    recipeListLiveData.setValue(recipes);
                    currentPositionPublisher.setValue(currentPositionPublisher.getValue());
                }, Throwable::printStackTrace);

        subscriptions.add(disposable);
    }

    public void updatePosition(int position){
        currentPositionPublisher.setValue(position);
    }

    public LiveData<List<DisplayableRecipeImage>> observeRecipes() {
        return recipeListLiveData;
    }

    public LiveData<DisplayableRecipe> observeCurrentRecipe(){
        return Transformations.switchMap(currentPositionPublisher, index -> {
            try {
                long id = recipeListLiveData.getValue().get(index).getId();
                return LiveDataReactiveStreams.fromPublisher(
                        recipeGateway.observeById(id).map(MainFragmentViewModel::toPresentation)
                );
            } catch (Throwable ex){
                return new MutableLiveData<>();
            }
        });
    }

    @Override
    protected void onCleared() {
        subscriptions.clear();
    }

    private static DisplayableRecipeImage toPresentationAsImage(Recipe recipe){
        return new DisplayableRecipeImage(
                recipe.getId(),
                recipe.getImages().get(0)
        );
    }

    private static DisplayableRecipe toPresentation(Recipe recipe){
        return new DisplayableRecipe(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPeople(),
                recipe.getCalories(),
                recipe.getIngredients(),
                recipe.getImages(),
                recipe.getTags()
        );
    }

    public long getCurrentId() {
        try {
            Integer currentIndex = currentPositionPublisher.getValue();
            List<DisplayableRecipeImage> recipes = recipeListLiveData.getValue();
            return recipes.get(currentIndex).getId();
        } catch (Exception ex){
            return -1;
        }
    }
}
