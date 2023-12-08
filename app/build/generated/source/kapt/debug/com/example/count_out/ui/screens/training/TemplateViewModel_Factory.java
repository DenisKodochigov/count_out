package com.example.count_out.ui.screens.training;

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
public final class TemplateViewModel_Factory implements Factory<TrainingViewModel> {
  private final Provider<ErrorApp> errorAppProvider;

  private final Provider<DataRepository> dataRepositoryProvider;

  public TemplateViewModel_Factory(Provider<ErrorApp> errorAppProvider,
      Provider<DataRepository> dataRepositoryProvider) {
    this.errorAppProvider = errorAppProvider;
    this.dataRepositoryProvider = dataRepositoryProvider;
  }

  @Override
  public TrainingViewModel get() {
    return newInstance(errorAppProvider.get(), dataRepositoryProvider.get());
  }

  public static TemplateViewModel_Factory create(Provider<ErrorApp> errorAppProvider,
      Provider<DataRepository> dataRepositoryProvider) {
    return new TemplateViewModel_Factory(errorAppProvider, dataRepositoryProvider);
  }

  public static TrainingViewModel newInstance(ErrorApp errorApp, DataRepository dataRepository) {
    return new TrainingViewModel(errorApp, dataRepository);
  }
}
