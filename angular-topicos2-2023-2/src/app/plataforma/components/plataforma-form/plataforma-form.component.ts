import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Fabricante } from 'src/app/models/fabricante.model';
import { Plataforma } from 'src/app/models/plataforma.model';
import { PlataformaService } from 'src/app/services/plataforma.service';
import { FabricanteService } from 'src/app/services/fabricante.service';

@Component({
  selector: 'app-plataforma-form',
  templateUrl: './plataforma-form.component.html',
  styleUrls: ['./plataforma-form.component.css']
})
export class PlataformaFormComponent {

  formGroup: FormGroup;
  fabricantes: Fabricante[] = [];

  constructor(private formBuilder: FormBuilder,
              private plataformaService: PlataformaService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private fabricanteService: FabricanteService,
              ) {

    const plataforma: Plataforma = this.activatedRoute.snapshot.data['plataforma'];
    this.formGroup = formBuilder.group({
      id:[(plataforma && plataforma.id)? plataforma.id : null],
      nome:[(plataforma && plataforma.nome)? plataforma.nome : '', Validators.required],
      descricao:[(plataforma && plataforma.descricao)? plataforma.descricao : '', Validators.required],
      anoLancamento:[(plataforma && plataforma.anoLancamento)? plataforma.anoLancamento : '', Validators.required],
      fabricante:[(plataforma && plataforma.fabricante)? plataforma.fabricante.id : '', Validators.required]
    })
  }

  ngOnInit(): void {
    this.fabricanteService.findAll(0,27).subscribe(data => {
      this.fabricantes = data;
    });
  }

  


  salvar() {
    if (this.formGroup.valid) {
      const novaPlataforma = this.formGroup.value;
      if (novaPlataforma.id == null) {

        this.plataformaService.save(novaPlataforma).subscribe({
          next: (plataformaCadastrada) => {
            this.router.navigateByUrl('/plataformas/list');
          },
          error: (err) => {
            console.log('Erro ao salvar' + JSON.stringify(err));
          }
        })
      }
      else {

        this.plataformaService.update(novaPlataforma).subscribe({
          next: (plataformaCadastrada) => {
            this.router.navigateByUrl('/plataformas/list');
          },
          error: (err) => {
            console.log('Erro ao salvar' + JSON.stringify(err));
          }
        })
      }
    }
  }
}
