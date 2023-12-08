package com.example.count_out.ui.screens.trainings;

import com.example.count_out.data.DataRepository;
import com.example.count_out.entity.ErrorApp;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class TemplatesViewModel_Factory implements Factory<TrainingsViewModel> {
  private final Provider<ErrorApp> errorAppProvider;

  private final Provider<DataRepository> dataRepositoryProvider;

  public TemplatesViewModel_Factory(Provider<ErrorApp> errorAppProvider,
      Provider<DataRepository> dataRepositoryProvider) {
    this.errorAppProvider = errorAppProvider;
    this.dataRepositoryProvider = dataRepositoryProvider;
  }

  @Override
  public TrainingsViewModel get() {
    return newInstance(errorAppProvider.get(), dataRepositoryProvider.get());
  }

  public static TemplatesViewModel_Factory create(Provider<ErrorApp> errorAppProvider,
      Provider<DataRepository> dataRepositoryProvider) {
    return new TemplatesViewModel_Factory(errorAppProvider, dataRepositoryProvider);
  }

  public static TrainingsViewModel newInstance(ErrorApp errorApp, DataRepository dataRepository) {
    return new TrainingsViewModel(errorApp, dataRepository);
  }
}
