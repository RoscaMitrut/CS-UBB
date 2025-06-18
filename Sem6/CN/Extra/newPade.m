function fAprox = newPade(x, c, m, k)
  if length(c) < m+k+2
      error('Sunt necesari cel putin %i coeficienti c', m+k+2)
  end

  C = zeros(k);
  y = zeros(k, 1);
  for i = 1:k
    for j = 1:k
      if m+i-j>0
        C(i, j) = c(m+i-j+1);
      end
    end
    y(i) = -c(m+i+1);
  end
  b = C \ y; 

  b = [1; b];

  a = zeros(m+1, 1);
  for j=1:m+1
    for l=1:min(j, k+1) % pentru l>k+1 avem b(l) = 0
      a(j) = a(j) + c(j-l+1)*b(l);
    end
  end

  fAproxNumarator = polyval(a(m+1:-1:1), x);
  fAproxNumitor = polyval(b(k+1:-1:1), x);
  fAprox = fAproxNumarator./fAproxNumitor;
end