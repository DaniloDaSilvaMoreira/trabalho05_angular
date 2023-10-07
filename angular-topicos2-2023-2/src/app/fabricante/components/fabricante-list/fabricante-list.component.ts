import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Fabricante } from 'src/app/models/fabricante.model';
import { FabricanteService } from 'src/app/services/fabricante.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-fabricante-list',
  templateUrl: './fabricante-list.component.html',
  styleUrls: ['./fabricante-list.component.css']
})
export class FabricanteListComponent implements OnInit {

  tableColumns: string[] = ['id-column', 'nome-column', 'ano-fundacao-column', 'acoes-column'];
  fabricantes: Fabricante[] = [];

  totalRegistros = 0;
  pageSize = 2;
  pagina = 0;
  filtro: string = "";


  constructor(private fabricanteService: FabricanteService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.carregarFabricantes();
    this.carregarTotalRegistros();
  }

  carregarFabricantes() {
    // se existe dados no filtro
    if (this.filtro) {
      this.fabricanteService.findByNome(this.filtro, this.pagina, this.pageSize).subscribe(data => {
        this.fabricantes = data;
      });
    } else {
      // buscando todos os fabricantes
      this.fabricanteService.findAll(this.pagina, this.pageSize).subscribe(data => {
        this.fabricantes = data;
      });
    }
  }

  carregarTotalRegistros() {
    // se existe dados no filtro
    if (this.filtro) {
      this.fabricanteService.countByNome(this.filtro).subscribe(data => {
        this.totalRegistros = data;
      });
    } else {
      this.fabricanteService.count().subscribe(data => {
        this.totalRegistros = data;
      });
    }
  }

    // Método para paginar os resultados
    paginar(event: PageEvent): void {
      this.pagina = event.pageIndex;
      this.pageSize = event.pageSize;
      this.carregarFabricantes();
    }

    aplicarFiltro() {
      this.carregarFabricantes();
      this.carregarTotalRegistros();
    }

    openConfirmationDialog(fabricante: Fabricante) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        width: '400px',
        data: { message: 'Tem certeza de que deseja excluir este fabricante?' }
      });
  
      dialogRef.afterClosed().subscribe({
        next: (result) => {
          if (result) {
            this.fabricanteService.delete(fabricante).subscribe({
              next: () => {
                this.fabricantes = this.fabricantes.filter(u => u !== fabricante);
                this.carregarTotalRegistros();
                this.carregarFabricantes();
                console.log('Fabricante excluído com sucesso');
              },
              error: (error) => {
                console.error('Erro ao excluir Fabricante:', error);
              }
            });
          }
        }
      });
    }
  
}
