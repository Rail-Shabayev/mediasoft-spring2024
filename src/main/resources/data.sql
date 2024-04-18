INSERT INTO public.product(
	uuid, name, description, type, price, quantity, date_quantity_updated, date_created)
	SELECT gen_random_uuid (),
       'rail','test', 'ELECTRONICS', '10', '1', '2003-03-25 05:30:30', '2003-03-25'
	FROM generate_series(1, 1000000);