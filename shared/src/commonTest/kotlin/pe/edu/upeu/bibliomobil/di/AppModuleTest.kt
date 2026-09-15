package pe.edu.upeu.bibliomobil.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import pe.edu.upeu.bibliomobil.data.repository.LibroRepositorioEnMemoria
import pe.edu.upeu.bibliomobil.domain.repository.LibroRepository
import pe.edu.upeu.bibliomobil.domain.usecase.ListarLectoresUseCase
import pe.edu.upeu.bibliomobil.domain.usecase.ListarLibrosUseCase
import pe.edu.upeu.bibliomobil.domain.usecase.RegistrarLectorUseCase
import pe.edu.upeu.bibliomobil.domain.usecase.RegistrarLibroUseCase
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertSame

class AppModuleTest {
    @AfterTest fun cerrarKoin() {
        GlobalContext.stopKoin()
    }

    @Test fun resuelveLibroRepositoryComoImplementacionEnMemoria() {
        assertIs<LibroRepositorioEnMemoria>(iniciarKoin().get<LibroRepository>())
    }

    @Test fun mantieneUnaUnicaInstanciaDelRepositorio() {
        val koin = iniciarKoin()
        assertSame(koin.get<LibroRepository>(), koin.get<LibroRepository>())
    }

    @Test fun resuelveLosCuatroCasosDeUso() {
        val koin = iniciarKoin()
        koin.get<RegistrarLibroUseCase>()
        koin.get<ListarLibrosUseCase>()
        koin.get<RegistrarLectorUseCase>()
        koin.get<ListarLectoresUseCase>()
    }

    private fun iniciarKoin() = startKoin {
        modules(dataModule, domainModule)
    }.koin
}
