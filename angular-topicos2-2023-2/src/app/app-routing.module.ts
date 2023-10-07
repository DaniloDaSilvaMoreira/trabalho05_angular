import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: 'generos', loadChildren: () => import('./genero/genero.module').then(m => m.GeneroModule)},
  {path: 'fabricantes', loadChildren: () => import('./fabricante/fabricante.module').then(m => m.FabricanteModule)},
  {path: 'plataformas', loadChildren: () => import('./plataforma/plataforma.module').then(m => m.PlataformaModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
