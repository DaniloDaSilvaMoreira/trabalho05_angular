import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Genero } from 'src/app/models/genero.model';
import { GeneroService } from 'src/app/services/genero.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-genero-list',
  templateUrl: './genero-list.component.html',
  styleUrls: ['./genero-list.component.css']
})
export class GeneroListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'nome-column', 'acoes-column'];
  generos: Genero[] = [];

  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";


  constructor(private generoService: GeneroService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.carregarGeneros();
    this.carregarTotalRegistros();
  }

  carregarGeneros() {
    // se existe dados no filtro
    if (this.filtro) {
      this.generoService.findByNome(this.filtro, this.pagina, this.pageSize).subscribe(data => {
        this.generos = data;
      });
    } else {
      // buscando todos os generos
      this.generoService.findAll(this.pagina, this.pageSize).subscribe(data => {
        this.generos = data;
      });
    }
  }

  carregarTotalRegistros() {
    // se existe dados no filtro
    if (this.filtro) {
      this.generoService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;
      });
    } else {
      this.generoService.count().subscribe(data => {
        this.totalRegistros = data;
      });
    }
  }

  /// Método para paginar os resultados
  paginar(event: PageEvent): void {
    this.pagina = event.pageIndex;
    this.pageSize = event.pageSize;
    this.carregarGeneros();
  }

  aplicarFiltro() {
    this.carregarGeneros();
    this.carregarTotalRegistros();
  }
  
  excluir(genero: Genero) {

    if (genero.id != null) {

      this.generoService.delete(genero).subscribe({
        next: (generoCadastrado) => {
          this.ngOnInit();
        },
        error: (err) => {
          console.log('Erro ao excluir' + JSON.stringify(err));
        }
      })
    }
}

openConfirmationDialog(genero: Genero) {
  const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
    width: '400px',
    data: { message: 'Tem certeza de que deseja excluir este genero?' }
  });

  dialogRef.afterClosed().subscribe({
    next: (result) => {
      if (result) {
        this.generoService.delete(genero).subscribe({
          next: () => {
            this.generos = this.generos.filter(u => u !== genero);
            this.carregarTotalRegistros();
            this.carregarGeneros();
            console.log('Genero excluído com sucesso');
          },
          error: (error) => {
            console.error('Erro ao excluir genero:', error);
          }
        });
      }
    }
  });
}

}
